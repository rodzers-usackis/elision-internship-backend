package eu.elision.pricing.listeners;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.events.ScrapingFinishedEvent;
import eu.elision.pricing.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event listener for {@link ScrapingFinishedEvent}.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ScrapingFinishedEventListener {

    private final AlertService alertService;


    /**
     * Handles the {@link ScrapingFinishedEvent} event
     * by creating {@link Alert}s.
     *
     * @param event the event containing the start time of the event chain
     */
    @EventListener
    public void handle(ScrapingFinishedEvent event) {

        log.debug("Handling ScrapingFinishedEvent event {}", event);
        alertService.createNewAlerts(event.getEventChainStartTime());
    }


}
