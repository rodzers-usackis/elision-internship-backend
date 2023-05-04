package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ScraperService} using jsoup.
 */
@RequiredArgsConstructor
@Service
public class ScraperServiceImpl implements ScraperService {

    private final Logger logger = LoggerFactory.getLogger(ScraperServiceImpl.class);
    private final PriceScrapingConfigRepository priceScrapingConfigRepository;
    private final PriceRepository priceRepository;
    private final HttpRequestService httpRequestService;


    @Override
    public Price scrapePrice(PriceScrapingConfig psc) throws IOException, NumberFormatException {
        String html = httpRequestService.getHtml(psc.getUrl());
        Document document = Jsoup.parse(html);
        String priceString = document.select(psc.getCssSelector()).text();

        logger.debug("""
                        
            Scraped price: {},
            Scraped from: {}.
            """, priceString, psc.getUrl());

        double price = psc.parsePriceValue(priceString);

        logger.debug("""
            Parsed value: {}
            """, price);

        return Price.builder().product(psc.getProduct())
            .retailerCompany(psc.getRetailerCompany())
            .timestamp(LocalDateTime.now())
            .amount(price)
            .build();
    }

}
