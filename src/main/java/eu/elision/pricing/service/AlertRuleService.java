package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertRuleDto;
import java.util.List;
import java.util.UUID;

/**
 * Service for {@link AlertRule}s.
 */
public interface AlertRuleService {
    void deleteAllByIdIn(List<UUID> id);

    AlertRuleDto createAlertRule(User user, AlertRuleDto alertRuleDto);

}
