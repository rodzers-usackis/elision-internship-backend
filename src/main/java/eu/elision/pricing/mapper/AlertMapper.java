package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.dto.AlertDto;

/**
 * Mapper for {@link Alert}s.
 */
public interface AlertMapper {

    AlertDto domainToDto(Alert alert);

}
