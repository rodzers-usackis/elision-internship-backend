package eu.elision.pricing.repository;

import eu.elision.pricing.domain.Alert;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link Alert}s.
 */
public interface AlertRepository extends JpaRepository<Alert, UUID> {
    List<Alert> findAllByClientCompany_Id(UUID clientCompanyId);

    int countAlertByClientCompanyAndReadIsFalse(UUID clientCompanyId);
}
