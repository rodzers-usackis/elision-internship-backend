package eu.elision.pricing.listeners;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.events.ProductPriceScrapedEvent;
import eu.elision.pricing.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event listener for {@link ProductPriceScrapedEvent}.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ProductPriceScrapedEventListener {

    private final AlertService alertService;

    /**
     * Handles the {@link ProductPriceScrapedEvent} event
     * by creating {@link Alert}s.
     *
     * @param event the event containing the product and the new prices
     */
    @EventListener
    public void handleEvent(ProductPriceScrapedEvent event) {

        log.debug("Handling ProductPriceScrapedEvent event {}", event);
        alertService.createAlerts(event.getProduct(), event.getNewPrices());

    }
}
