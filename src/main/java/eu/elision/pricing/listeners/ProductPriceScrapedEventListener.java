package eu.elision.pricing.listeners;

import eu.elision.pricing.events.ProductPriceScrapedEvent;
import eu.elision.pricing.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductPriceScrapedEventListener {

    private final AlertService alertService;

    @EventListener
    public void handleEvent(ProductPriceScrapedEvent event) {

        log.debug("Handling ProductPriceScrapedEvent event {}", event);
        alertService.createAlerts(event.getProduct(), event.getNewPrices());

    }
}
