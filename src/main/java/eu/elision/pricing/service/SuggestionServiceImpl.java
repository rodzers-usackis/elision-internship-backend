package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.SuggestedPrice;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.repository.SuggestedPriceRepository;
import java.time.LocalDateTime;
import java.util.List;
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


        suggestedPrice = suggestedPriceRepository.save(suggestedPrice);

        return suggestedPrice;


    }
}
