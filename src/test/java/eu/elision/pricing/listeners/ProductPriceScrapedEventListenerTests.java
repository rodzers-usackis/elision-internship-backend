package eu.elision.pricing.listeners;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.events.ProductPriceScrapedEvent;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import eu.elision.pricing.events.ProductsPricesScrapedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
class ProductPriceScrapedEventListenerTests {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    private ProductPriceScrapedEventListener productPriceScrapedEventListener;

    @Captor
    private ArgumentCaptor<ProductPriceScrapedEvent> eventCaptor;

    @Captor
    private ArgumentCaptor<ProductsPricesScrapedEvent> eventCaptorV2;


    @Test
    void eventReceivedCorrectly() {
        Product product = Product.builder()
            .id(UUID.randomUUID())
            .name("Test product")
            .build();

        Price price = Price.builder()
            .id(UUID.randomUUID())
            .amount(100.0)
            .product(product)
            .build();

        Price price2 = Price.builder()
            .id(UUID.randomUUID())
            .amount(200.0)
            .product(product)
            .build();

        ProductPriceScrapedEvent
            event = new ProductPriceScrapedEvent(product, List.of(price, price2));

        applicationEventPublisher.publishEvent(event);

        verify(productPriceScrapedEventListener).handleEvent(eventCaptor.capture());

        ProductPriceScrapedEvent capturedEvent = eventCaptor.getValue();

        assertEquals(event.getProduct().getId(), capturedEvent.getProduct().getId());
        assertEquals(event.getNewPrices().size(), capturedEvent.getNewPrices().size());
    }


    @Test
    void eventV2ReceivedCorrectly() {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("Test product")
                .build();

        Price price = Price.builder()
                .id(UUID.randomUUID())
                .amount(100.0)
                .product(product)
                .build();

        Price price2 = Price.builder()
                .id(UUID.randomUUID())
                .amount(200.0)
                .product(product)
                .build();

        Map<UUID, List<UUID>> productToPricesMap = Map.of(product.getId(), List.of(price.getId(), price2.getId()));
        ProductsPricesScrapedEvent event = new ProductsPricesScrapedEvent(productToPricesMap);

        applicationEventPublisher.publishEvent(event);

        verify(productPriceScrapedEventListener).handleEventV2(eventCaptorV2.capture());
        ProductsPricesScrapedEvent capturedEvent = eventCaptorV2.getValue();

        assertEquals(event.getProductToPricesMap().size(), capturedEvent.getProductToPricesMap().size());
    }
}