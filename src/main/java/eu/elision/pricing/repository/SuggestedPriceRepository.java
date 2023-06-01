package eu.elision.pricing.repository;

import eu.elision.pricing.domain.SuggestedPrice;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestedPriceRepository extends JpaRepository<SuggestedPrice, UUID> {

    List<SuggestedPrice> findAllByTimestampAfter(LocalDateTime after);
}
