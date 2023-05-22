package eu.elision.pricing.dto.notifications;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationSettingsWithAlertRulesDto {

    private boolean alertsActive;
    private boolean notifyViaEmail;
    private List<AlertRuleDto> alertRules;

}
