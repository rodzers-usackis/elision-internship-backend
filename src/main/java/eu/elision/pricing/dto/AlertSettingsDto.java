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
    private UUID uuid;
    private boolean emailNotifications;
    private String emailAddress;
    private AlertStorageDuration alertStorageDuration;
    private UserDto user;
}
