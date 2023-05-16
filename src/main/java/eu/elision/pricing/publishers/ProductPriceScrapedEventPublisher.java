package eu.elision.pricing.publishers;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.events.ProductPriceScrapedEvent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceScrapedEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(Product product, List<Price> newPrices) {
        ProductPriceScrapedEvent event = ProductPriceScrapedEvent.builder()
            .product(product)
            .newPrices(newPrices)
            .build();
        applicationEventPublisher.publishEvent(event);
    }
}
