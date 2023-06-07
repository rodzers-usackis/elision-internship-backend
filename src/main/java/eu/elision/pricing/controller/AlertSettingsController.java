package eu.elision.pricing.controller;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertSettingsDto;
import eu.elision.pricing.service.AlertSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for {@link AlertSettings}s.
 */
@CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alert-settings")
public class AlertSettingsController {

    private final AlertSettingsService alertSettingsService;

    /**
     * Retrieves the alert settings for the authenticated user.
     *
     * @param user the authenticated user
     * @return the updated alert settings
     */
    @CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})    @GetMapping
    public ResponseEntity<AlertSettingsDto> getAlertSettings(@AuthenticationPrincipal User user) {
        AlertSettingsDto alertSettingsDto = alertSettingsService.getAlertSettings(user);

        return ResponseEntity.ok(alertSettingsDto);
    }

    /**
     * Updates the alert settings for the authenticated user.
     *
     * @param user             the authenticated user
     * @param alertSettingsDto AlertSettingsDto with updated values
     * @return no content
     */
    @CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
    @PatchMapping
    public ResponseEntity<Void> updateNotificationSettings(
        @AuthenticationPrincipal User user,
        @RequestBody AlertSettingsDto alertSettingsDto
    ) {

        alertSettingsService.updateNotificationSettings(user, alertSettingsDto);
        return ResponseEntity.noContent().build();
    }

}