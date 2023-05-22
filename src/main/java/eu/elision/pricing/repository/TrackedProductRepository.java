package eu.elision.pricing.repository;

import eu.elision.pricing.domain.TrackedProduct;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JPA epository for {@link TrackedProduct}s.
 */
public interface TrackedProductRepository extends JpaRepository<TrackedProduct, UUID> {

    List<TrackedProduct> findTrackedProductByClientCompanyId(UUID clientCompanyId);

    @Transactional
    @Modifying
    @Query("DELETE FROM TrackedProduct t WHERE t.clientCompany.id = :companyId "
        + "AND t.id IN :trackedProductIds")
    void deleteTrackedProducts(@Param("companyId") UUID companyId,
                               @Param("trackedProductIds") List<UUID> trackedProductIds);

    List<TrackedProduct> findTrackedProductsByProduct_Id(UUID productId);
}
