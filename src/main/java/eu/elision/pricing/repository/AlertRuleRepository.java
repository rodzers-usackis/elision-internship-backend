package eu.elision.pricing.repository;

import eu.elision.pricing.domain.AlertRule;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlertRuleRepository extends JpaRepository<AlertRule, UUID> {


    @Query("SELECT COUNT(DISTINCT ar) FROM AlertRule ar "
        + " WHERE ar.id IN (:id) "
        + " AND ar.alertSettings.clientCompany.id = :clientCompanyId")
    long countAllByIdAndNotificationSettingsClientCompany_Id(List<UUID> id, UUID clientCompanyId);

    void deleteAllByIdIn(List<UUID> id);


}
