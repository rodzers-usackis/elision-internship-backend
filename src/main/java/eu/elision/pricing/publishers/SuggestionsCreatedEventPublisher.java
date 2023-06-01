package eu.elision.pricing.publishers;

import eu.elision.pricing.events.SuggestionsCreatedEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SuggestionsCreatedEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(LocalDateTime eventChainStartTime) {
        SuggestionsCreatedEvent event = SuggestionsCreatedEvent.builder()
            .eventChainStartTime(eventChainStartTime)
            .build();

        applicationEventPublisher.publishEvent(event);
    }

}
