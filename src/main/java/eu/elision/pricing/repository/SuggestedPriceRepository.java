package eu.elision.pricing.repository;

import eu.elision.pricing.domain.SuggestedPrice;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestedPriceRepository extends JpaRepository<SuggestedPrice, UUID> {

}
