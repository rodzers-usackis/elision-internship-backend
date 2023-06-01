package eu.elision.pricing.listeners;

import eu.elision.pricing.events.SuggestionsCreatedEvent;
import eu.elision.pricing.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SuggestionsCreatedEventListener {

    private final EmailService emailService;

    @EventListener
    public void handleEvent(SuggestionsCreatedEvent event) {

        log.info("SuggestionsCreatedEvent received: {}", event);

        emailService.sendEventAfterPriceScraping(event.getEventChainStartTime());


    }

}
