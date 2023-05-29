package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.dto.AlertRuleDto;

/**
 * Mapper for {@link AlertRule} and {@link AlertRuleDto}.
 */
public interface AlertRuleMapper {

    AlertRuleDto domainToDto(AlertRule alertRule);

    AlertRule dtoToDomain(AlertRuleDto alertRuleDto);
}
