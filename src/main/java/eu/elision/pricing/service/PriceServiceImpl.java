package eu.elision.pricing.service;

import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PriceService}.
 */
@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    private final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);
    private final ScraperService scraperService;
    private final PriceRepository priceRepository;
    private final PriceScrapingConfigRepository priceScrapingConfigRepository;

    @Override
    public void scrapeAndSavePrices() {
        priceScrapingConfigRepository.findAll().forEach(psc -> {
            if (psc.isActive()) {
                try {
                    priceRepository.save(scraperService.scrapePrice(psc));
                } catch (Exception e) {
                    logger.error("Error while scraping price for product: {} (id:{})",
                        psc.getProduct().getName(), psc.getId(), e);
                }
            }
        });
    }
}
