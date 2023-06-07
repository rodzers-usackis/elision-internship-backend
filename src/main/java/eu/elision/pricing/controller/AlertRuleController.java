package eu.elision.pricing.controller;

import eu.elision.pricing.domain.AlertRule;
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

/**
 * Controller class for handling requests
 * related to {@link AlertRule}s.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alert-rules")
@CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
public class AlertRuleController {

    private final AlertRuleService alertRuleService;


    /**
     * Returns all {@link AlertRule}s for the
     * currently logged in {@link User}.
     *
     * @param user currently logged in {@link User}
     * @return a {@link ResponseEntity} containing a list of {@link AlertRuleDto}s
     */
    @GetMapping
    public ResponseEntity<List<AlertRuleDto>> getAllAlertRules(
        @AuthenticationPrincipal User user
    ) {

        return ResponseEntity.ok(alertRuleService.getAllAlertRulesByUser(user));

    }

    /**
     * Creates a new {@link AlertRule} for the
     * currently logged in {@link User}.
     *
     * @param user         currently logged in {@link User}
     * @param alertRuleDto {@link AlertRuleToCreateDto} containing the data
     *                     for the new {@link AlertRule}
     * @return a {@link ResponseEntity} containing
     *     the {@link AlertRuleDto} of the newly created {@link AlertRule}
     */
    @PostMapping
    public ResponseEntity<AlertRuleDto> createAlertRule(
        @AuthenticationPrincipal User user,
        @RequestBody AlertRuleToCreateDto alertRuleDto
    ) {

        return new ResponseEntity<>(alertRuleService.createAlertRule(user, alertRuleDto),
            HttpStatus.CREATED);

    }

    /**
     * Deletes all {@link AlertRule}s with the given ids.
     * Only {@link AlertRule}s belonging to the currently
     * logged in {@link User} will be deleted.
     *
     * @param user currently logged in {@link User}
     * @param ids  list of ids of the {@link AlertRule}s to delete
     * @return a {@link ResponseEntity} with no content
     */
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
