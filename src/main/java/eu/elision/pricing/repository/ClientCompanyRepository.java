package eu.elision.pricing.repository;
import eu.elision.pricing.domain.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany, UUID> {
}
