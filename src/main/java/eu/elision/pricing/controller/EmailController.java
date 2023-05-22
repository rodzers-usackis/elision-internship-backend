package eu.elision.pricing.controller;

import eu.elision.pricing.events.ProductsPricesScrapedEvent;
import eu.elision.pricing.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/sendOutEmails")
    public ResponseEntity<String> sendOutEmails(@RequestBody ProductsPricesScrapedEvent productsPricesScrapedEvent) {
        String status = emailService.sendOutEmails(productsPricesScrapedEvent);

        return ResponseEntity.ok(status);
    }
}
