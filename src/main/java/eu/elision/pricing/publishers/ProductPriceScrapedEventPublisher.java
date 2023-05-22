package eu.elision.pricing.publishers;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.events.ProductPriceScrapedEvent;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import eu.elision.pricing.events.ProductsPricesScrapedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publisher for {@link ProductPriceScrapedEvent}.
 */
@RequiredArgsConstructor
@Component
public class ProductPriceScrapedEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Publishes a {@link ProductPriceScrapedEvent}.
     *
     * @param product   the product whose price has been scraped
     * @param newPrices the new prices of the given product
     */
    public void publish(Product product, List<Price> newPrices) {
        ProductPriceScrapedEvent event = ProductPriceScrapedEvent.builder()
            .product(product)
            .newPrices(newPrices)
            .build();
        applicationEventPublisher.publishEvent(event);
    }

    public void publish(Map<UUID, List<UUID>> productToPricesMap) {
        ProductsPricesScrapedEvent event = ProductsPricesScrapedEvent.builder()
            .productToPricesMap(productToPricesMap)
            .build();

        applicationEventPublisher.publishEvent(event);
    }
}
