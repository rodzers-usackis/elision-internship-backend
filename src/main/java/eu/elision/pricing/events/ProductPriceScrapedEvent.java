package eu.elision.pricing.events;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Event that is published when a product's price is scraped.
 */
@Data
@Builder
@AllArgsConstructor
public class ProductPriceScrapedEvent {

    private Product product;
    private List<Price> newPrices;


}
