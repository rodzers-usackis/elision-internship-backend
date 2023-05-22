package eu.elision.pricing.service;

import eu.elision.pricing.domain.emailService.EmailDetails;
import eu.elision.pricing.events.ProductsPricesScrapedEvent;

public interface EmailService {

    String sendOutEmails(ProductsPricesScrapedEvent productsPricesScrapedEvent);

    String sendEmailToUser (EmailDetails emailDetails);
}
