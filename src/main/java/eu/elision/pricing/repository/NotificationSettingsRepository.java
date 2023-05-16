package eu.elision.pricing.repository;

import eu.elision.pricing.domain.NotificationSettings;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, UUID> {


    NotificationSettings findByClientCompany_Id(UUID clientCompanyId);

}
