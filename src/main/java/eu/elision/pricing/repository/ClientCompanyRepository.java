package eu.elision.pricing.repository;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.TrackedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany, UUID> {

    @Query(
            value = "INSERT INTO tracked_product (id, product_purchase_cost, product_sell_price, client_company_id, product_id) VALUES (:trackedProduct.id, :trackedProduct.productPurchaseCost, :trackedProduct.productSellPrice, :companyId, :trackedProduct.product.id)",
            nativeQuery = true
    )
    void saveTrackedProduct(@Param("companyId") UUID companyId, @Param("trackedProduct") TrackedProduct trackedProduct);


    @Query(
            value = "DELETE FROM tracked_product WHERE client_company_id = :companyId AND product_id = :trackedProductId",
            nativeQuery = true
    )
    TrackedProduct findTrackedProductById(@Param("trackedProductId") UUID trackedProductId, @Param("companyId") UUID companyId);
}
