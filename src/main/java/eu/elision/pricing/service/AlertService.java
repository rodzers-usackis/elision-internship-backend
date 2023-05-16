package eu.elision.pricing.service;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertDto;
import java.util.List;

/**
 * Service for {@link Alert}s.
 * Responsible for creating and retrieving alerts.
 */
public interface AlertService {
    List<AlertDto> getUsersAlerts(User user);

    void createAlerts(Product product, List<Price> prices);
}
