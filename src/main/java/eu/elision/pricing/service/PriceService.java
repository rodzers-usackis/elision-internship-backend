package eu.elision.pricing.service;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.repository.PriceRepository;

/**
 * Interface for the price service.
 * Handles saving and retrieving {@link Price} objects
 * to and from {@link PriceRepository}.
 */
public interface PriceService {

    void scrapeAndSavePrices();

}
