package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.events.ProductPriceScrapedEvent;
import eu.elision.pricing.mapper.PriceHistoryMapper;
import eu.elision.pricing.publishers.ProductPriceScrapedEventPublisher;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ProductPriceScrapedEventPublisher productPriceScrapedEventPublisher;

    @Override
    public void scrapeAndSavePrices() {

        List<PriceScrapingConfig> priceScrapingConfigs =
            priceScrapingConfigRepository.findAllByActiveTrue();


        Map<Product, List<PriceScrapingConfig>> productToPriceScrapingConfigMap =
            priceScrapingConfigs.stream()
                .collect(Collectors.groupingBy(PriceScrapingConfig::getProduct));



        productToPriceScrapingConfigMap.forEach((product, pscs) -> {

            List<Price> pricesForCurrentProduct = new ArrayList<>();

            pscs.forEach(psc -> {
                try {
                    Price scrapedPrice = scraperService.scrapePrice(psc);
                    priceRepository.save(scrapedPrice);
                    pricesForCurrentProduct.add(scrapedPrice);
                } catch (Exception e) {
                    log.error(
                        "Error while scraping price for Product (name:{}; id:{}). "
                            + "PriceScrapingConfig id:{})",
                        psc.getProduct().getId(), psc.getProduct().getName(), psc.getId());
                }
            });

            productPriceScrapedEventPublisher.publish(product, pricesForCurrentProduct);

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
