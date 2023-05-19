package eu.elision.pricing.controller;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertDto;
import eu.elision.pricing.service.AlertService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for {@link Alert}s.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alerts")
public class AlertsController {

    private final AlertService alertService;

    /**
     * Returns all alerts for the authenticated user's {@link eu.elision.pricing.domain.ClientCompany}.
     *
     * @param user the authenticated user
     * @return a list of {@link AlertDto}s
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<AlertDto>> getAlerts(@AuthenticationPrincipal User user) {

        List<AlertDto> alerts = alertService.getUsersAlerts(user);
        return ResponseEntity.ok(alerts);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/unread/count")
    public ResponseEntity<Integer> getUnreadAlertCount(@AuthenticationPrincipal User user) {

        int unreadAlertCount = alertService.getUnreadAlertCount(user);
        return ResponseEntity.ok(unreadAlertCount);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping()
    public ResponseEntity<List<AlertDto>> markAlertsAsRead(@RequestBody List<AlertDto> alertDto) {

        List<AlertDto> updatedAlerts = alertService.markAlertsAsRead(alertDto);
        return ResponseEntity.ok(updatedAlerts);
    }
}
