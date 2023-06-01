package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertRuleDto;
import eu.elision.pricing.dto.AlertRuleToCreateDto;
import eu.elision.pricing.dto.RetailerCompanyDto;
import eu.elision.pricing.exceptions.NotFoundException;
import eu.elision.pricing.mapper.AlertRuleMapper;
import eu.elision.pricing.repository.AlertRuleRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for {@link AlertRule}s.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AlertRuleServiceImpl implements AlertRuleService {


    private final ProductRepository productRepository;
    private final RetailerCompanyRepository retailerCompanyRepository;
    private final AlertRuleRepository alertRuleRepository;
    private final AlertRuleMapper alertRuleMapper;

    @Transactional
    @Override
    public void deleteAllByIdIn(User user, List<UUID> ids) {

        long count = alertRuleRepository.countAllByAlertSettings_IdAndIdIn(user.getId(), ids);

        List<AlertRule> alertRules = alertRuleRepository.findAllById(ids);

        alertRules.forEach(alertRule -> {
            Product product = alertRule.getProduct();
            product.getAlertRules().remove(alertRule);
            productRepository.save(product);
        });

        if (count != ids.size()) {
            log.error(">>> At least 1 alert rule not found for the user. Alert rule count: "
                + count + ", number of IDs to delete: " + ids.size() + ", user id: "
                + user.getId() + ", ids: "
                + ids.toString() + ".");
            throw new NotFoundException("At least 1 alert rule not found for the user");
        }

        alertRuleRepository.deleteAllByIdIn(ids);

    }

    @Transactional
    @Override
    public AlertRuleDto createAlertRule(User user, AlertRuleToCreateDto alertRuleDto) {

        //log all ids of products present in repo
        //use log.debug
        List<Product> products = productRepository.findAll();
        log.debug(">>> products present in repo:");
        for (Product product : products) {
            log.debug(">>Product id: " + product.getId());
        }


        Product product = productRepository.findById(alertRuleDto.getProduct().getId())
            .orElseThrow(() -> new NotFoundException("Product not found (id: "
                + alertRuleDto.getProduct().getId() + ")"));


        List<RetailerCompany> retailerCompanies;

        if (alertRuleDto.getRetailerCompanies() != null) {
            retailerCompanies =
                retailerCompanyRepository.findAllById(alertRuleDto.getRetailerCompanies()
                    .stream()
                    .map(
                        RetailerCompanyDto::getId)
                    .toList());

            if (retailerCompanies.size() != alertRuleDto.getRetailerCompanies().size()) {
                throw new NotFoundException("At least 1 retailer company not found");
            }

        } else {
            retailerCompanies = new ArrayList<>();
        }

        AlertRule alertRule = AlertRule.builder()
            .price(alertRuleDto.getPriceThreshold())
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .product(product)
            .retailerCompanies(retailerCompanies)
            .build();

        AlertSettings alertSettings =
            user.getAlertSettings();

        alertRule.setAlertSettings(alertSettings);
        alertRule = alertRuleRepository.save(alertRule);


        return alertRuleMapper.domainToDto(alertRule);

    }

    @Transactional
    @Override
    public List<AlertRuleDto> getAllAlertRulesByUser(User user) {
        List<AlertRule> alertRules =
            alertRuleRepository.findAllByAlertSettings_Id(user.getAlertSettings().getId());

        if (alertRules == null || alertRules.isEmpty()) {
            throw new NotFoundException("No alert rules found");
        }

        return alertRules.stream().map(alertRuleMapper::domainToDto).toList();


    }

    @Transactional
    @Override
    public void updateAlertRule(User user, AlertRuleDto alertRuleDto) {

        AlertRule alertRule = alertRuleRepository.findById(alertRuleDto.getId())
            .orElseThrow(() -> new NotFoundException("Alert rule not found (id: "
                + alertRuleDto.getId() + ")"));

        if (!alertRule.getAlertSettings().getId().equals(user.getAlertSettings().getId())) {
            throw new NotFoundException("Alert rule not found (id: "
                + alertRuleDto.getId() + ")");
        }

        alertRule.setPrice(alertRuleDto.getPriceThreshold());
        alertRule.setPriceComparisonType(alertRuleDto.getPriceComparisonType());


        alertRuleRepository.save(alertRule);

    }

}
