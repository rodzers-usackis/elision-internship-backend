package eu.elision.pricing.repository;

import eu.elision.pricing.domain.AlertSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA repository for {@link AlertSettings}.
 */
public interface AlertSettingsRepository extends JpaRepository<AlertSettings, UUID> {
    AlertSettings findAlertSettingsByUser_Id(UUID userId);
    AlertSettings findAlertSettingsById(UUID id);
}
