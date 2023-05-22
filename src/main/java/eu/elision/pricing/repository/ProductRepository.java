package eu.elision.pricing.repository;


import eu.elision.pricing.domain.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JPA Repository for {@link Product}.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByEan(String ean);

    Product findByManufacturerCode(String manufacturerCode);

    @Query("SELECT p FROM Product p JOIN FETCH p.alertRules")
    List<Product> findAllWithAlertRules();
}
