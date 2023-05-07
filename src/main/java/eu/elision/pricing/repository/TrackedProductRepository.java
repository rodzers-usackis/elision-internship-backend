package eu.elision.pricing.repository;
import eu.elision.pricing.domain.TrackedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TrackedProductRepository extends JpaRepository <TrackedProduct, UUID> {
}
