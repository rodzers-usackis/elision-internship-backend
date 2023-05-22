package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertSettingsDto;
import eu.elision.pricing.dto.notifications.NotificationSettingsDto;
import eu.elision.pricing.dto.notifications.NotificationSettingsWithAlertRulesDto;

/**
 * Service for {@link AlertSettings}.
 * Responsible for creating and retrieving alerts.
 */
public interface AlertSettingsService {

    AlertSettingsDto getAlertSettings(User user);
    AlertSettings createAlertSettings(AlertSettings alertSettings);
    AlertSettingsDto getNotificationSettings(User user);

    void updateNotificationSettings(User user,
                                    AlertSettingsDto notificationSettingsDto);
}
