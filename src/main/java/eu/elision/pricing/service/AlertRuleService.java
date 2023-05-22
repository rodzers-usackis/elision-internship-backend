package eu.elision.pricing.service;

import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.notifications.AlertRuleDto;
import java.util.List;
import java.util.UUID;

public interface AlertRuleService {
    void deleteAllByIdIn(List<UUID> id);

    AlertRuleDto createAlertRule(User user, AlertRuleDto alertRuleDto);

}
