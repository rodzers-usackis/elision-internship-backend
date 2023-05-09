package eu.elision.pricing.repository;

import eu.elision.pricing.domain.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
