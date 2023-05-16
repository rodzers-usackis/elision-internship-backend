package eu.elision.pricing.dto.trackedproduct;

import eu.elision.pricing.domain.TrackedProduct;
import lombok.Builder;
import lombok.Data;

/**
 * Additional DTO for {@link TrackedProduct}.
 * This DTO is used for updating the price of a {@link TrackedProduct}.
 */
@Data
@Builder
public class TrackedProductPriceUpdateDto {
    private String id;
    private double productPurchaseCost;
    private double productSellPrice;
    private boolean isTracked;
}
