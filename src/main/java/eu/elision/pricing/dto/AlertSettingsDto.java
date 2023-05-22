package eu.elision.pricing.dto;

import eu.elision.pricing.domain.AlertStorageDuration;
import eu.elision.pricing.domain.AlertSettings;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * DTO for {@link AlertSettings}.
 */
@Data
@Builder
public class AlertSettingsDto {
    private UUID id;
    private boolean alertsActive;
    private boolean notifyViaEmail;
    private String emailAddress;
    private int alertStorageDuration;
}
