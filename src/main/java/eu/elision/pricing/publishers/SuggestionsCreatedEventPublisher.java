package eu.elision.pricing.publishers;

import eu.elision.pricing.events.SuggestionsCreatedEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publisher for the {@link SuggestionsCreatedEvent}.
 */
@RequiredArgsConstructor
@Component
public class SuggestionsCreatedEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Publishes the {@link SuggestionsCreatedEvent}.
     *
     * @param eventChainStartTime the timestamp of the event chain start time
     */
    public void publish(LocalDateTime eventChainStartTime) {
        SuggestionsCreatedEvent event = SuggestionsCreatedEvent.builder()
            .eventChainStartTime(eventChainStartTime)
            .build();

        applicationEventPublisher.publishEvent(event);
    }

}
