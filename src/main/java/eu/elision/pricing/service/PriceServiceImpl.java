package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.mapper.PriceHistoryMapper;
import eu.elision.pricing.publishers.ProductPriceScrapedEventPublisher;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
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
    public void scrapeAndSavePricesV2() {
        List<PriceScrapingConfig> priceScrapingConfigs =
            priceScrapingConfigRepository.findAllByActiveTrue();


        Map<Product, List<PriceScrapingConfig>> productToPriceScrapingConfigMap =
            priceScrapingConfigs.stream()
                .collect(Collectors.groupingBy(PriceScrapingConfig::getProduct));

        productToPriceScrapingConfigMap.forEach((product, pscs) -> {

            Map<UUID, List<UUID>> productToPricesMap = new HashMap<>();

            pscs.forEach(psc -> {
                try {
                    Price scrapedPrice = scraperService.scrapePrice(psc);
                    priceRepository.save(scrapedPrice);

                    List<UUID> pricesForCurrentProduct = productToPricesMap.getOrDefault(
                        psc.getProduct().getId(), new ArrayList<>());
                    pricesForCurrentProduct.add(scrapedPrice.getId());
                    productToPricesMap.put(psc.getProduct().getId(), pricesForCurrentProduct);
                } catch (Exception e) {
                    log.error(
                        "Error while scraping price for Product (name:{}; id:{}). "
                            + "PriceScrapingConfig id:{})",
                        psc.getProduct().getId(), psc.getProduct().getName(), psc.getId());
                }
            });

            productPriceScrapedEventPublisher.publish(productToPricesMap);

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

    @Override
    public Price getPriceById(UUID priceId) {
        return priceRepository.getPriceById(priceId);
    }

    @Override
    public List<Price> getPricesByProductId(UUID productId) {
        return priceRepository.getPricesByProduct_Id(productId);
    }

    @Override
    public void scrapeProductsPrices(List<UUID> productIds) {

        List<PriceScrapingConfig> allPriceScrapingConfigs =
            priceScrapingConfigRepository.findAllByActiveTrueAndProduct_IdIn(productIds);


        if (allPriceScrapingConfigs.isEmpty()) {
            log.error("No active PriceScrapingConfigs for Product with id: {}", productIds);
            return;
        }


        Map<Product, List<PriceScrapingConfig>> productToPriceScrapingConfigMap =
            allPriceScrapingConfigs.stream()
                .collect(Collectors.groupingBy(PriceScrapingConfig::getProduct));

        productToPriceScrapingConfigMap.forEach((product, pscs) -> {

            List<Price> pricesForCurrentProduct = new ArrayList<>();

            pscs.forEach(psc -> {
                try {
                    log.debug("Scraping price for Product (name:{}; id:{}). "
                            + "PriceScrapingConfig id:{})",
                        psc.getProduct().getName(), psc.getProduct().getId(), psc.getId());

                    Price scrapedPrice = scraperService.scrapePrice(psc);
                    scrapedPrice = priceRepository.save(scrapedPrice);
                    pricesForCurrentProduct.add(scrapedPrice);
                } catch (Exception e) {
                    log.error(
                        "Error while scraping price for Product (name:{}; id:{}). "
                            + "PriceScrapingConfig id:{})",
                        psc.getProduct().getId(), psc.getProduct().getName(), psc.getId());
                }
            });

            log.info("Prices for Product (name:{}; id:{}) scraped successfully",
                product.getName(), product.getId());
            log.info("Prices: {}", pricesForCurrentProduct.stream().map(Price::getAmount).collect(
                Collectors.toList()));
            productPriceScrapedEventPublisher.publish(product, pricesForCurrentProduct);

        });

    }


}
