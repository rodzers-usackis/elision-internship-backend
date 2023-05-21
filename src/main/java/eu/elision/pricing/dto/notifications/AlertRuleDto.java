package eu.elision.pricing.dto.notifications;

import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.dto.RetailerCompanyDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertRuleDto {

    private UUID id;
    private ProductDto product;
    private double priceThreshold;
    private PriceComparisonType priceComparisonType;
    private List<RetailerCompanyDto> retailerCompanies;

}
