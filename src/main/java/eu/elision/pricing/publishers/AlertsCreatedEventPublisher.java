package eu.elision.pricing.publishers;

import eu.elision.pricing.events.AlertsCreatedEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlertsCreatedEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(LocalDateTime eventChainStartTime) {
        AlertsCreatedEvent event = AlertsCreatedEvent.builder()
            .eventChainStartTime(eventChainStartTime)
            .build();

        applicationEventPublisher.publishEvent(event);
    }

}
