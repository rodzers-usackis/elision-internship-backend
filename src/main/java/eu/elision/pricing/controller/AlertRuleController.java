package eu.elision.pricing.controller;

import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertRuleDto;
import eu.elision.pricing.dto.AlertRuleToCreateDto;
import eu.elision.pricing.service.AlertRuleService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alert-rules")
@CrossOrigin(origins = "http://localhost:3000")
public class AlertRuleController {

    private final AlertRuleService alertRuleService;


    @GetMapping
    public ResponseEntity<List<AlertRuleDto>> getAllAlertRules(
        @AuthenticationPrincipal User user
    ) {

        return ResponseEntity.ok(alertRuleService.getAllAlertRulesByUser(user));

    }

    @PostMapping
    public ResponseEntity<AlertRuleDto> createAlertRule(
        @AuthenticationPrincipal User user,
        @RequestBody AlertRuleToCreateDto alertRuleDto
    ) {

        return new ResponseEntity<>(alertRuleService.createAlertRule(user, alertRuleDto),
            HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAlertRules(
        @AuthenticationPrincipal User user,
        @RequestBody List<UUID> ids
    ) {

        log.debug(">>> Deleting alert rules with ids: " + ids.toString() + ".");

        alertRuleService.deleteAllByIdIn(user, ids);

        log.debug(">>> Alert rules deleted.");

        return ResponseEntity.noContent().build();

    }


    @PatchMapping
    public ResponseEntity<Void> updateAlertRule(
        @AuthenticationPrincipal User user,
        @RequestBody AlertRuleDto alertRuleDto
    ) {

        alertRuleService.updateAlertRule(user, alertRuleDto);

        return ResponseEntity.ok().build();

    }

}
