package eu.elision.pricing.repository;
import eu.elision.pricing.domain.TrackedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TrackedProductRepository extends JpaRepository <TrackedProduct, UUID> {
}
