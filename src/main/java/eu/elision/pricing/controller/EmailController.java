package eu.elision.pricing.controller;

import eu.elision.pricing.service.EmailService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for {@link EmailService}.
 * This controller is used to send out emails to users
 * whenever the price of a product changes.
 * Execution depends on:
 *
 * @see EmailService
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    /**
     * Sends out emails with price alerts to users.
     *
     * @return a response entity with the status of the email service
     * @deprecated this method was only used for testing purposes
     *     emails should not be sent out after a post request,
     *     but rather after a price scraping event
     */
    @PostMapping("/sendOutEmails")
    public ResponseEntity<String> sendOutEmails() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(5);
        String status = emailService.sendEventAfterPriceScraping(startTime);

        return ResponseEntity.ok(status);
    }
}
