package eu.elision.pricing.repository;

import eu.elision.pricing.domain.PriceScrapingConfig;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link PriceScrapingConfig} entities.
 */
public interface PriceScrapingConfigRepository extends JpaRepository<PriceScrapingConfig, UUID> {
    List<PriceScrapingConfig> findAllByActiveTrue();

    List<PriceScrapingConfig> findAllByActiveTrueAndProduct_IdIn(List<UUID> productIds);

}
