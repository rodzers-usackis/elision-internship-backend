package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.dto.AlertSettingsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AlertSettingsMapper}.
 * Maps {@link AlertSettings}s to {@link AlertSettingsDto}s.
 */
@RequiredArgsConstructor
@Component
public class AlertSettingsMapperImpl implements AlertSettingsMapper {
    @Override
    public AlertSettingsDto domainToDto(AlertSettings alertSettings) {
        return AlertSettingsDto.builder()
            .id(alertSettings.getId())
            .notifyViaEmail(alertSettings.isNotifyViaEmail())
            .alertsActive(alertSettings.isAlertsActive())
            .emailAddress(alertSettings.getEmailAddress())
            .alertStorageDuration(alertSettings.getAlertStorageDuration())
            .build();
    }
}
