package eu.elision.pricing.repository;
import eu.elision.pricing.domain.RetailerCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RetailerCompanyRepository extends JpaRepository<RetailerCompany, UUID> {
}
