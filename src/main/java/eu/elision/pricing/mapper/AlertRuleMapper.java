package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.dto.notifications.AlertRuleDto;

public interface AlertRuleMapper {

    AlertRuleDto domainToDto(AlertRule alertRule);
    AlertRule dtoToDomain(AlertRuleDto alertRuleDto);
}
