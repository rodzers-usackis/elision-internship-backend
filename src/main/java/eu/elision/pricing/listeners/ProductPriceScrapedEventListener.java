package eu.elision.pricing.listeners;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.emailservice.EmailDetailsDto;
import eu.elision.pricing.events.ProductPriceScrapedEvent;
import eu.elision.pricing.events.ProductsPricesScrapedEvent;
import eu.elision.pricing.service.AlertService;
import eu.elision.pricing.service.EmailService;
import eu.elision.pricing.service.PriceService;
import eu.elision.pricing.service.ProductService;
import java.util.List;
import java.util.UUID;
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
    private final ProductService productService;
    private final PriceService priceService;
    private final EmailService emailService;

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

    /**
     * Handles the {@link ProductsPricesScrapedEvent} event
     * by creating {@link Alert}s
     * and sending an {@link EmailDetailsDto} to the affected {@link User}s.
     *
     * @param event the event containing the product and the new prices
     */
    @EventListener
    public void handleEventV2(ProductsPricesScrapedEvent event) {

        for (UUID productId : event.getProductToPricesMap().keySet()) {
            log.debug("Handling ProductPriceScrapedEvent event {}", event);
            Product product = productService.getProduct(productId);
            List<Price> productPrices = priceService.getPricesByProductId(productId);
            alertService.createAlerts(product, productPrices);
        }

        emailService.sendOutEmails(event);
    }
}
