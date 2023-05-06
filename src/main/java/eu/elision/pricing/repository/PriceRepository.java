package eu.elision.pricing.repository;

import eu.elision.pricing.domain.Price;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for {@link Price} entities.
 */
public interface PriceRepository extends JpaRepository<Price, UUID> {
}
