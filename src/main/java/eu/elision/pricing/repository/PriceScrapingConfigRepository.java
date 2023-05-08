package eu.elision.pricing.repository;

import eu.elision.pricing.domain.PriceScrapingConfig;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for {@link PriceScrapingConfig} entities.
 */
public interface PriceScrapingConfigRepository extends JpaRepository<PriceScrapingConfig, UUID> {
}