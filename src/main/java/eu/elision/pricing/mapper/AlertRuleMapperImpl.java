package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.dto.AlertRuleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlertRuleMapperImpl implements AlertRuleMapper {

    private final RetailerCompanyMapper retailerCompanyMapper;
    private final ProductMapper productMapper;

    @Override
    public AlertRuleDto domainToDto(AlertRule alertRule) {

        return AlertRuleDto.builder()
            .id(alertRule.getId())
            .priceComparisonType(alertRule.getPriceComparisonType())
            .priceThreshold(alertRule.getPrice())
            .product(productMapper.domainToDto(alertRule.getProduct()))
            .retailerCompanies(
                alertRule.getRetailerCompanies().stream().map(retailerCompanyMapper::domainToDto)
                    .toList())
            .build();
    }

    @Override
    public AlertRule dtoToDomain(AlertRuleDto alertRuleDto) {
        return AlertRule.builder()
            .id(alertRuleDto.getId())
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(productMapper.dtoToDomain(alertRuleDto.getProduct()))
            .retailerCompanies(
                alertRuleDto.getRetailerCompanies().stream().map(retailerCompanyMapper::dtoToDomain)
                    .toList())
            .build();
    }
}
