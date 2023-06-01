package eu.elision.pricing.listeners;

import eu.elision.pricing.events.AlertsCreatedEvent;
import eu.elision.pricing.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlertsCreatedEventListener {

    private final SuggestionService suggestionService;

    @EventListener
    public void handleEvent(AlertsCreatedEvent event) {
        log.debug("Handling AlertsCreatedEvent event {}", event);
        suggestionService.suggestNewPrices(event.getEventChainStartTime());
    }
}
