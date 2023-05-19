package eu.elision.pricing.controller;

import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertSettingsDto;
import eu.elision.pricing.service.AlertSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alert-settings")
public class AlertSettingsController {

    private final AlertSettingsService alertSettingsService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<AlertSettingsDto> getAlertSettings(@AuthenticationPrincipal User user) {
        AlertSettingsDto alertSettingsDto = alertSettingsService.getAlertSettings(user);

        return ResponseEntity.ok(alertSettingsDto);
    }
}
