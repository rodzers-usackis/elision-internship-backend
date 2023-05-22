package eu.elision.pricing.service;

import eu.elision.pricing.dto.emailService.EmailDetailsDto;
import eu.elision.pricing.events.ProductsPricesScrapedEvent;

/**
 * Service for sending emails.
 */
public interface EmailService {
    String sendOutEmails(ProductsPricesScrapedEvent productsPricesScrapedEvent);

    String sendEmailToUser (EmailDetailsDto emailDetailsDto);
}
