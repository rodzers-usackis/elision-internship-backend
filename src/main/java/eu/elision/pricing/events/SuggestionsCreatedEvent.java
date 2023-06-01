package eu.elision.pricing.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SuggestionsCreatedEvent {
    private final LocalDateTime eventChainStartTime;
}
