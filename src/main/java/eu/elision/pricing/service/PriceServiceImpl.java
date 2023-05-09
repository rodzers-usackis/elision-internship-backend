package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.mapper.PriceHistoryMapper;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PriceService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    private final ScraperService scraperService;
    private final PriceRepository priceRepository;
    private final PriceScrapingConfigRepository priceScrapingConfigRepository;
    private final PriceHistoryMapper priceHistoryMapper;

    @Override
    public void scrapeAndSavePrices() {
        priceScrapingConfigRepository.findAll().forEach(psc -> {
            if (psc.isActive()) {
                try {
                    priceRepository.save(scraperService.scrapePrice(psc));
                } catch (Exception e) {
                    log.error("Error while scraping price for product: {} (id:{})",
                        psc.getProduct().getName(), psc.getId(), e);
                }
            }
        });
    }

    @Override
    public PriceHistoryDto getPriceHistory(UUID productId, LocalDateTime before,
                                           LocalDateTime after) {
        List<Price> prices =
            priceRepository.findAllByProduct_IdAndTimestampBeforeAndTimestampAfter(productId,
                before, after);

        return priceHistoryMapper.domainToDto(prices);
    }

    @Override
    public PriceHistoryDto getPriceHistory(UUID productId, LocalDate before, LocalDate after) {
        return getPriceHistory(productId, before.atStartOfDay(), after.atStartOfDay());
    }
}
