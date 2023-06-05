package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.SuggestedPrice;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.publishers.SuggestionsCreatedEventPublisher;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.SuggestedPriceRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link SuggestionService}.
 */
@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestedPriceRepository suggestedPriceRepository;
    private final TrackedProductRepository trackedProductRepository;
    private final PriceRepository priceRepository;
    private final SuggestionsCreatedEventPublisher suggestionsCreatedEventPublisher;

    @Override
    public SuggestedPrice calculateSuggestedPrice(TrackedProduct clientsTrackedProduct,
                                                  List<Price> competitorsCurrentPrices) {

        OptionalDouble lowestPriceOptional = competitorsCurrentPrices.stream()
            .mapToDouble(Price::getAmount)
            .min();

        if (lowestPriceOptional.isEmpty()) {
            // If there are no prices, don't suggest anything
            return null;
        }

        double lowestPrice = lowestPriceOptional.getAsDouble();

        SuggestedPrice suggestedPrice = SuggestedPrice.builder()
            .clientCompany(clientsTrackedProduct.getClientCompany())
            .product(clientsTrackedProduct.getProduct())
            .timestamp(LocalDateTime.now())
            .build();

        if (clientsTrackedProduct.getMinPrice() != null) {
            // If the client has set a minimum price, don't suggest lower than that
            suggestedPrice.setSuggestedPrice(
                Math.max(clientsTrackedProduct.getMinPrice(), lowestPrice));
        } else if (lowestPrice > clientsTrackedProduct.getProductSellPrice()) {
            // If the lowest price is higher than the client's current price
            // suggest the lowest price
            suggestedPrice.setSuggestedPrice(lowestPrice);
        } else if (lowestPrice < clientsTrackedProduct.getProductPurchaseCost()) {
            // If the lowest price is lower than the client's purchase cost
            // suggest the purchase cost + 5%
            suggestedPrice.setSuggestedPrice(clientsTrackedProduct.getProductPurchaseCost() * 1.05);
        } else {
            // Otherwise, don't suggest anything
            return null;
        }


        if (suggestedPrice.getSuggestedPrice() == clientsTrackedProduct.getProductSellPrice()) {
            // No need to suggest a price if it's the same as the current price
            return null;
        }

        Optional<SuggestedPrice> existingSuggestedPrice =
            suggestedPriceRepository.findFirstByProduct_IdOrderByTimestampDesc(
                suggestedPrice.getProduct().getId());

        if (existingSuggestedPrice.isPresent()) {
            if (existingSuggestedPrice.get().getSuggestedPrice() ==
                suggestedPrice.getSuggestedPrice()) {
                // No need to suggest a price if it's the same as the previous suggested price
                return null;
            }
        }


        suggestedPrice = suggestedPriceRepository.save(suggestedPrice);

        return suggestedPrice;


    }

    @Override
    public void suggestPrices(Map<Product, List<Price>> productToPricesMap) {


        List<SuggestedPrice> suggestedPrices = new ArrayList<>();

        productToPricesMap.keySet().forEach(
            product -> {

                List<TrackedProduct> trackedProducts =
                    trackedProductRepository.findTrackedProductsByProduct_Id(product.getId());

                List<Price> competitorsCurrentPrices = productToPricesMap.get(product);

                trackedProducts.forEach(
                    trackedProduct -> {
                        SuggestedPrice suggestedPrice = calculateSuggestedPrice(
                            trackedProduct, competitorsCurrentPrices);
                        if (suggestedPrice != null) {
                            suggestedPrices.add(suggestedPrice);
                        }
                    }
                );

            }
        );


    }


    @Override
    public void suggestNewPrices(LocalDateTime pricesCreatedAfter) {

        List<Price> newPrices = priceRepository.findAllByTimestampAfter(pricesCreatedAfter);

        Map<Product, List<Price>> productToPricesMap = newPrices.stream()
            .collect(java.util.stream.Collectors.groupingBy(Price::getProduct));

        suggestPrices(productToPricesMap);


        suggestionsCreatedEventPublisher.publish(pricesCreatedAfter);

    }
}
