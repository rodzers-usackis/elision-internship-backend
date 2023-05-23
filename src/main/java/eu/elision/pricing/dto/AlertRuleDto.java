package eu.elision.pricing.dto;

import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.dto.RetailerCompanyDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * Represents an alert rule that a user can set.
 */
@Data
@Builder
public class AlertRuleDto {

    private UUID id;
    private ProductDto product;
    private double priceThreshold;
    private PriceComparisonType priceComparisonType;
    private List<RetailerCompanyDto> retailerCompanies;

}
