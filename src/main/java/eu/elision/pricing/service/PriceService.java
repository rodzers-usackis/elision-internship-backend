package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.repository.PriceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.scheduling.annotation.Async;

/**
 * Interface for the price service.
 * Handles saving and retrieving {@link Price} objects
 * to and from {@link PriceRepository}.
 */
public interface PriceService {

    void scrapeAndSavePrices();

    //void scrapeAndSavePricesV2();

    @Async("threadPoolTaskExecutor")
    void scrapeProductsPrices(Collection<UUID> productIds);

    PriceHistoryDto getPriceHistory(UUID productId, LocalDateTime before, LocalDateTime after);

    PriceHistoryDto getPriceHistory(UUID productId, LocalDate before, LocalDate after);

    Price getPriceById(UUID priceId);

    List<Price> getPricesByProductId(UUID productId);
}
