package eu.elision.pricing.repository;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.RetailerCompany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The repository for {@link Price} entities.
 */
public interface PriceRepository extends JpaRepository<Price, UUID> {


    List<Price> findAllByProduct_IdAndTimestampBeforeAndTimestampAfter(UUID productId,
                                                                       LocalDateTime before,
                                                                       LocalDateTime after);

    List<Price> findAllByProduct_IdAndTimestampBeforeAndTimestampAfterAndRetailerCompany_Id(UUID productId,
                                                                                            LocalDateTime before,
                                                                                            LocalDateTime after,
                                                                                            UUID retailerCompanyId);



}
