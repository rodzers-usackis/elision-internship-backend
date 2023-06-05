package eu.elision.pricing.publishers;

import eu.elision.pricing.events.AlertsCreatedEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publisher for {@link AlertsCreatedEvent}.
 * Publishes the event when all new alerts are created.
 */
@RequiredArgsConstructor
@Component
public class AlertsCreatedEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Publishes a {@link AlertsCreatedEvent}
     * when all new alerts are created.
     *
     * @param eventChainStartTime the start time of the event chain
     */
    public void publish(LocalDateTime eventChainStartTime) {
        AlertsCreatedEvent event = AlertsCreatedEvent.builder()
            .eventChainStartTime(eventChainStartTime)
            .build();

        applicationEventPublisher.publishEvent(event);
    }

}
