package eu.elision.pricing.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Event that is published when the price scraping is finished.
 */
@Data
@Builder
@AllArgsConstructor
public class ScrapingFinishedEvent {
    private LocalDateTime eventChainStartTime;
}
