package eu.elision.pricing.repository;

import eu.elision.pricing.domain.AlertRule;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JPA repository for {@link AlertRule}.
 */
public interface AlertRuleRepository extends JpaRepository<AlertRule, UUID> {


    void deleteAllByIdIn(List<UUID> id);

    long countAllByAlertSettings_IdAndIdIn(UUID userId, List<UUID> id);

    List<AlertRule> findAllByAlertSettings_Id(UUID userId);

    AlertRule findByProductAndAlertSettings_Id(UUID productId, UUID alertSettingsId);
}
