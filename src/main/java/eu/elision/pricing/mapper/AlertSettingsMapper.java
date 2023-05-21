package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.dto.AlertSettingsDto;

/**
 * Mapper for {@link AlertSettings}.
 */
public interface AlertSettingsMapper {

    AlertSettingsDto domainToDto(AlertSettings alertSettings);
}
