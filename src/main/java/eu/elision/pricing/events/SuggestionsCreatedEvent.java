package eu.elision.pricing.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Event that is published when the price suggestions are created.
 */
@Data
@Builder
@AllArgsConstructor
public class SuggestionsCreatedEvent {
    private final LocalDateTime eventChainStartTime;
}
