package eu.elision.pricing.dto;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.PriceComparisonType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link Alert}.
 * Contains the information that will be shown
 * to the user about the price change of a product
 * at a certain retailer company.
 *
 */
@Data
@Builder
public class AlertDto {
    private UUID id;

    private boolean read;

    private LocalDateTime timestamp;

    private double price;

    private double alertRulePriceThreshold;

    @Enumerated(EnumType.STRING)
    private PriceComparisonType priceComparisonType;

    private ProductDto product;

    private RetailerCompanyDto retailerCompany;
}
