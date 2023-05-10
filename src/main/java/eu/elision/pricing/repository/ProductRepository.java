package eu.elision.pricing.repository;


import eu.elision.pricing.domain.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for {@link Product}.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
