package eu.elision.pricing.dto;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.PriceComparisonType;
import java.time.LocalDateTime;
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

    private boolean read;

    private LocalDateTime timestamp;

    private double price;

    private PriceComparisonType priceComparisonType;

    private ProductDto product;

    private RetailerCompanyDto retailerCompany;


}
