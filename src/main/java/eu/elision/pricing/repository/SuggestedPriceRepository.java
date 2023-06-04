package eu.elision.pricing.repository;

import eu.elision.pricing.domain.SuggestedPrice;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for the {@link SuggestedPrice} entity.
 */
public interface SuggestedPriceRepository extends JpaRepository<SuggestedPrice, UUID> {

    List<SuggestedPrice> findAllByTimestampAfter(LocalDateTime after);

    Optional<SuggestedPrice> findFirstByProduct_IdOrderByTimestampDesc(UUID productId);
}
