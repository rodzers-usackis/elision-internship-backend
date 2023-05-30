package eu.elision.pricing.dto.trackedproduct;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link TrackedProduct}.
 */
@Data
@Builder
public class TrackedProductWithDetailsDto {
    private String id;
    private double productPurchaseCost;
    private double productSellPrice;
    private Double minPrice;
    private boolean isTracked;
    private String ean;
    private String manufacturerCode;
    private ProductDto product;

}
