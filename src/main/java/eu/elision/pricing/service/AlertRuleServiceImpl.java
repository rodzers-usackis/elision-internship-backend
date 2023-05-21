package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.RetailerCompanyDto;
import eu.elision.pricing.dto.notifications.AlertRuleDto;
import eu.elision.pricing.mapper.AlertRuleMapper;
import eu.elision.pricing.repository.AlertRuleRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AlertRuleServiceImpl implements AlertRuleService {


    private final ProductRepository productRepository;
    private final RetailerCompanyRepository retailerCompanyRepository;
    private final AlertRuleRepository alertRuleRepository;
    private final AlertRuleMapper alertRuleMapper;

    @Override
    public void deleteAllByIdIn(List<UUID> id) {
        //TODO: implement
    }

    @Transactional
    @Override
    public AlertRuleDto createAlertRule(User user, AlertRuleDto alertRuleDto) {
        Product product = productRepository.findById(alertRuleDto.getProduct().getId())
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        List<RetailerCompany> retailerCompanies;

        if (alertRuleDto.getRetailerCompanies() != null) {
            retailerCompanies =
                retailerCompanyRepository.findAllById(alertRuleDto.getRetailerCompanies()
                    .stream()
                    .map(
                        RetailerCompanyDto::getId)
                    .toList());
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
}
