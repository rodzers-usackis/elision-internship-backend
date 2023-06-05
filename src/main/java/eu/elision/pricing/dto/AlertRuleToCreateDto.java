package eu.elision.pricing.dto;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.PriceComparisonType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for creating a new {@link AlertRule}.
 * Contains the data for the new {@link AlertRule}.
 */
@Data
@Builder
public class AlertRuleToCreateDto {

    private ProductDto product;
    private double priceThreshold;
    @Enumerated(EnumType.STRING)
    private PriceComparisonType priceComparisonType;
    private List<RetailerCompanyDto> retailerCompanies;

}
