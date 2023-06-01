package eu.elision.pricing.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Event to be published when all new alerts are created.
 * Contains the start time of the event chain.
 */
@Data
@Builder
@AllArgsConstructor
public class AlertsCreatedEvent {
    private final LocalDateTime eventChainStartTime;
}
