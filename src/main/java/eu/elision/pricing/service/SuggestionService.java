package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.SuggestedPrice;
import eu.elision.pricing.domain.TrackedProduct;
import java.util.List;

public interface SuggestionService {

    SuggestedPrice calculateSuggestedPrice(TrackedProduct clientsTrackedProduct,
                                           List<Price> competitorsCurrentPrices);

}
