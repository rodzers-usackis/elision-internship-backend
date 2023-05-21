package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.dto.notifications.NotificationSettingsWithAlertRulesDto;

public interface NotificationSettingsMapper {

    NotificationSettingsWithAlertRulesDto domainToDto(AlertSettings alertSettings);

    AlertSettings dtoToDomain(
        NotificationSettingsWithAlertRulesDto notificationSettingsWithAlertRulesDto);
}
