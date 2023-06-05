package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertRuleDto;
import eu.elision.pricing.dto.AlertRuleToCreateDto;
import java.util.List;
import java.util.UUID;

/**
 * Service for {@link AlertRule}s.
 */
public interface AlertRuleService {
    void deleteAllByIdIn(User user, List<UUID> id);

    AlertRuleDto createAlertRule(User user, AlertRuleToCreateDto alertRuleDto);

    List<AlertRuleDto> getAllAlertRulesByUser(User user);

    void updateAlertRule(User user, AlertRuleDto alertRuleDto);

}
