package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceScrapingConfig;
import java.io.IOException;

/**
 * Interface for the scraper service.
 * It is responsible for scraping the prices from the web.
 */
public interface ScraperService {

    Price scrapePrice(PriceScrapingConfig psc) throws IOException;

}
