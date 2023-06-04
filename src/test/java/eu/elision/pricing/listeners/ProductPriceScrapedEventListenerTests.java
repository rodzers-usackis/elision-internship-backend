package eu.elision.pricing.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.events.ScrapingFinishedEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    private ArgumentCaptor<ScrapingFinishedEvent> eventCaptor;


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

        ScrapingFinishedEvent event = ScrapingFinishedEvent.builder().eventChainStartTime(
            LocalDateTime.now()).build();

        applicationEventPublisher.publishEvent(event);

        verify(productPriceScrapedEventListener).handle(eventCaptor.capture());

        ScrapingFinishedEvent capturedEvent = eventCaptor.getValue();

        assertEquals(event.getEventChainStartTime(), capturedEvent.getEventChainStartTime());

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

        Map<UUID, List<UUID>> productToPricesMap =
            Map.of(product.getId(), List.of(price.getId(), price2.getId()));

        ScrapingFinishedEvent event = ScrapingFinishedEvent
            .builder()
            .eventChainStartTime(LocalDateTime.now())
            .build();

        applicationEventPublisher.publishEvent(event);

        verify(productPriceScrapedEventListener).handle(eventCaptor.capture());
        ScrapingFinishedEvent capturedEvent = eventCaptor.getValue();

        assertEquals(event.getEventChainStartTime(), capturedEvent.getEventChainStartTime());
    }
}