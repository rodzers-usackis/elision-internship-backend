package eu.elision.pricing.repository;

import eu.elision.pricing.domain.ClientCompany;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link ClientCompany}.
 */
public interface ClientCompanyRepository extends JpaRepository<ClientCompany, UUID> {
}
