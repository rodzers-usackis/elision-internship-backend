package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.repository.PriceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Interface for the price service.
 * Handles saving and retrieving {@link Price} objects
 * to and from {@link PriceRepository}.
 */
public interface PriceService {

    void scrapeAndSavePrices();

    PriceHistoryDto getPriceHistory(UUID productId, LocalDateTime before, LocalDateTime after);

    PriceHistoryDto getPriceHistory(UUID productId, LocalDate before, LocalDate after);


}
