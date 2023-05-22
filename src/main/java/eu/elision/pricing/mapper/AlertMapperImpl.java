package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.dto.AlertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AlertMapper}.
 * Maps {@link Alert}s to {@link AlertDto}s.
 */
@RequiredArgsConstructor
@Component
public class AlertMapperImpl implements AlertMapper {

    private final ProductMapper productMapper;
    private final RetailerCompanyMapper retailerCompanyMapper;

    @Override
    public AlertDto domainToDto(Alert alert) {
        return AlertDto.builder()
            .price(alert.getPrice().getAmount())
            .priceComparisonType(alert.getPriceComparisonType())
            .read(alert.isRead())
            .retailerCompany(retailerCompanyMapper.domainToDto(alert.getRetailerCompany()))
            .product(productMapper.domainToDto(alert.getPrice().getProduct()))
            .timestamp(alert.getTimestamp())
            .build();
    }
}
