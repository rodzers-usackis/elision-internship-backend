package eu.elision.pricing.repository;

import eu.elision.pricing.domain.AlertRule;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlertRuleRepository extends JpaRepository<AlertRule, UUID> {



    void deleteAllByIdIn(List<UUID> id);


}
