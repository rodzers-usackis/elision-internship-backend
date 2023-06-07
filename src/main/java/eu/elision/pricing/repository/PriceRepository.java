package eu.elision.pricing.repository;

import eu.elision.pricing.domain.Price;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link Price} entities.
 */
public interface PriceRepository extends JpaRepository<Price, UUID> {


    List<Price> findAllByProduct_IdAndTimestampBeforeAndTimestampAfter(UUID productId,
                                                                       LocalDateTime before,
                                                                       LocalDateTime after);

    List<Price> findAllByTimestampBetween(LocalDateTime before, LocalDateTime after);

    List<Price> findAllByTimestampAfter(LocalDateTime after);

    List<Price> findAllByProduct_IdAndTimestampBeforeAndTimestampAfterAndRetailerCompany_Id(
        UUID productId,
        LocalDateTime before,
        LocalDateTime after,
        UUID retailerCompanyId);


    Price getPriceById(UUID priceId);

    List<Price> getPricesByProduct_Id(UUID productId);

    Optional<Price> findFirstByProduct_IdAndRetailerCompany_IdAndTimestampBeforeOrderByTimestampDesc(
        UUID productId,
        UUID retailerCompanyId,
        LocalDateTime before);



}
