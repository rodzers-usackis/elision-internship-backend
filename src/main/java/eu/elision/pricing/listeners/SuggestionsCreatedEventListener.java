package eu.elision.pricing.listeners;

import eu.elision.pricing.events.SuggestionsCreatedEvent;
import eu.elision.pricing.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event listener that listens to the {@link SuggestionsCreatedEvent} and
 * sends an email to the client.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SuggestionsCreatedEventListener {

    private final EmailService emailService;

    /**
     * Handles the {@link SuggestionsCreatedEvent} and sends emails to clients.
     *
     * @param event the event with the timestamp of the event chain start time
     */
    @EventListener
    public void handleEvent(SuggestionsCreatedEvent event) {
        log.info("Handling SuggestionsCreatedEvent: {}", event);
        emailService.sendEmailsAfterPriceScraping(event.getEventChainStartTime());
    }

}
