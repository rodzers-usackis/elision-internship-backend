package eu.elision.pricing.dto.notifications;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationSettingsDto {
    private boolean alertsActive;
    private boolean notifyViaEmail;
}
