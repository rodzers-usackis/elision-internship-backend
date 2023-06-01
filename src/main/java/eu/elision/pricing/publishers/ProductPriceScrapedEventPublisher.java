package eu.elision.pricing.publishers;

import eu.elision.pricing.events.ScrapingFinishedEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publisher for {@link ScrapingFinishedEvent}.
 */
@RequiredArgsConstructor
@Component
public class ProductPriceScrapedEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Publishes a {@link ScrapingFinishedEvent}.
     *
     * @param startTime the start time of the event chain
     */
    public void publish(LocalDateTime startTime) {
        ScrapingFinishedEvent event = ScrapingFinishedEvent.builder()
            .eventChainStartTime(startTime)
            .build();

        applicationEventPublisher.publishEvent(event);
    }
}
