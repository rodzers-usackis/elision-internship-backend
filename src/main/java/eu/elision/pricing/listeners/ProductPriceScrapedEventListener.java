package eu.elision.pricing.listeners;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.events.ScrapingFinishedEvent;
import eu.elision.pricing.service.AlertService;
import eu.elision.pricing.service.EmailService;
import eu.elision.pricing.service.PriceService;
import eu.elision.pricing.service.ProductService;
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
public class ProductPriceScrapedEventListener {

    private final AlertService alertService;
    private final ProductService productService;
    private final PriceService priceService;
    private final EmailService emailService;

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
