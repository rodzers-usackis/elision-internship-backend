package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.SuggestedPrice;
import eu.elision.pricing.domain.TrackedProduct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SuggestionService {

    SuggestedPrice calculateSuggestedPrice(TrackedProduct clientsTrackedProduct,
                                           List<Price> competitorsCurrentPrices);

    void suggestPrices(Map<Product, List<Price>> productToPricesMap);

    void suggestNewPrices(LocalDateTime pricesCreatedAfter);

}
