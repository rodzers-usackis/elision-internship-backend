package eu.elision.pricing.repository;

import eu.elision.pricing.domain.AlertSettings;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * JPA repository for {@link AlertSettings}.
 */
public interface AlertSettingsRepository extends JpaRepository<AlertSettings, UUID> {
    AlertSettings findAlertSettingsByUser_Id(UUID userId);

    AlertSettings findAlertSettingsById(UUID id);
}
