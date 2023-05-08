package eu.elision.pricing.repository;
import eu.elision.pricing.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByEan(String ean);
}
