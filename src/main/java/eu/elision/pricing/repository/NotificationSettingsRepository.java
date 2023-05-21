package eu.elision.pricing.repository;

import eu.elision.pricing.domain.AlertSettings;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JPA repository for {@link AlertSettings}.
 */
public interface NotificationSettingsRepository extends JpaRepository<AlertSettings, UUID> {


    @Query("SELECT als FROM AlertSettings als "
        + " JOIN FETCH als.alertRules ar "
        + " WHERE als.clientCompany.id = :clientCompanyId")
    AlertSettings findByClientCompany_IdWithAlertRules(UUID clientCompanyId);

    AlertSettings findByClientCompany_Id(UUID clientCompanyId);

}
