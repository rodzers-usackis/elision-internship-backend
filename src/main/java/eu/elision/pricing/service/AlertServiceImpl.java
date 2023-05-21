package eu.elision.pricing.service;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertDto;
import eu.elision.pricing.mapper.AlertMapper;
import eu.elision.pricing.repository.AlertRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link AlertService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    @Transactional
    @Override
    public List<AlertDto> getUsersAlerts(User user) {
        List<Alert> alerts =
            alertRepository.findAllByClientCompany_Id(user.getClientCompany().getId());

        return alerts.stream().map(alertMapper::domainToDto).toList();

    }


    @Transactional
    @Override
    public void createAlerts(Product product, List<Price> prices) {
        if (prices.isEmpty()) {
            log.warn("No prices found for product {}", product.getId());
            return;
        }

        for (AlertRule alertRule : product.getAlertRules()) {
            List<Price> pricesToCompare =
                filterPricesByRetailerCompanies(prices, alertRule.getRetailerCompanies());

            if (pricesToCompare.isEmpty()) {
                continue;
            }

            Price price = selectPriceBasedOnComparisonType(pricesToCompare,
                alertRule.getPriceComparisonType());

            if (price == null) {
                continue;
            }

            if (isPriceMatched(alertRule, price.getAmount())) {
                createAlert(product, alertRule, price);
            }
        }
    }

    private List<Price> filterPricesByRetailerCompanies(List<Price> prices,
                                                        List<RetailerCompany> retailerCompanies) {
        if (retailerCompanies == null || retailerCompanies.isEmpty()) {
            return prices;
        }

        return prices.stream()
            .filter(price -> retailerCompanies.contains(price.getRetailerCompany()))
            .collect(Collectors.toList());
    }

    private Price selectPriceBasedOnComparisonType(List<Price> prices,
                                                   PriceComparisonType comparisonType) {
        if (prices.isEmpty()) {
            return null;
        }

        return (comparisonType == PriceComparisonType.LOWER)
            ? Collections.min(prices, Comparator.comparing(Price::getAmount))
            : Collections.max(prices, Comparator.comparing(Price::getAmount));
    }

    private boolean isPriceMatched(AlertRule alertRule, double price) {
        return (alertRule.getPriceComparisonType() == PriceComparisonType.LOWER
            && price <= alertRule.getPrice())
            || (alertRule.getPriceComparisonType() == PriceComparisonType.HIGHER
            && price >= alertRule.getPrice());
    }

    private void createAlert(Product product, AlertRule alertRule, Price price) {
        Alert alert = Alert.builder()
            .product(product)
            .clientCompany(alertRule.getAlertSettings().getClientCompany())
            .price(price)
            .priceComparisonType(alertRule.getPriceComparisonType())
            .retailerCompany(price.getRetailerCompany())
            .read(false)
            .timestamp(LocalDateTime.now())
            .build();

        alertRepository.save(alert);
    }

}
