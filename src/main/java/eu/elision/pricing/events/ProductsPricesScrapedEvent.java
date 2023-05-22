package eu.elision.pricing.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Event that is published when prices are scraped for products.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsPricesScrapedEvent {

    // Product ID -> List of Price IDs
    private Map<UUID, List<UUID>> productToPricesMap;
}
