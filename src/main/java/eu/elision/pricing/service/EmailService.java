package eu.elision.pricing.service;

import eu.elision.pricing.dto.emailservice.EmailDetailsDto;
import eu.elision.pricing.events.ProductsPricesScrapedEvent;
import java.time.LocalDateTime;

/**
 * Service for sending emails.
 */
public interface EmailService {
    String sendOutEmails(ProductsPricesScrapedEvent productsPricesScrapedEvent);

    String sendEmailToUser(EmailDetailsDto emailDetailsDto);

    String sendEventAfterPriceScraping(LocalDateTime pricesScrapedAfter);
}
