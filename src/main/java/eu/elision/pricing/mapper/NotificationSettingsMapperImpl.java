package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.dto.notifications.NotificationSettingsWithAlertRulesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationSettingsMapperImpl implements NotificationSettingsMapper {

    private final AlertRuleMapper alertRuleMapper;

    @Override
    public NotificationSettingsWithAlertRulesDto domainToDto(AlertSettings alertSettings) {
        return NotificationSettingsWithAlertRulesDto.builder()
            .alertRules(
                alertSettings.getAlertRules().stream().map(alertRuleMapper::domainToDto)
                    .toList())
            .notifyViaEmail(alertSettings.isNotifyViaEmail())
            .alertsActive(alertSettings.isAlertsActive())
            .build();
    }

    @Override
    public AlertSettings dtoToDomain(
        NotificationSettingsWithAlertRulesDto notificationSettingsWithAlertRulesDto) {
        return AlertSettings.builder()
            .alertRules(
                notificationSettingsWithAlertRulesDto.getAlertRules().stream().map(alertRuleMapper::dtoToDomain)
                    .toList())
            .notifyViaEmail(notificationSettingsWithAlertRulesDto.isNotifyViaEmail())
            .alertsActive(notificationSettingsWithAlertRulesDto.isAlertsActive())
            .build();
    }
}
