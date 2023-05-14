package eu.elision.pricing.dto.TrackedProduct;


import eu.elision.pricing.domain.TrackedProduct;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link TrackedProduct}.
 */
@Data
@Builder
public class TrackedProductDto {
    private String id;
    private double productPurchaseCost;
    private double productSellPrice;
    private boolean isTracked;
    private String productId;
    private String productEAN;
    private String clientCompanyId;
}
