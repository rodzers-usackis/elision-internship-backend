package eu.elision.pricing.repository;


import eu.elision.pricing.domain.RetailerCompany;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link RetailerCompany} entities.
 */
public interface RetailerCompanyRepository extends JpaRepository<RetailerCompany, UUID> {
}
