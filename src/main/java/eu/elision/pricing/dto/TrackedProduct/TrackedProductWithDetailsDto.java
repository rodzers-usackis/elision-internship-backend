package eu.elision.pricing.dto.TrackedProduct;

import eu.elision.pricing.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackedProductWithDetailsDto {
    private String id;
    private double productPurchaseCost;
    private double productSellPrice;
    private boolean isTracked;
    private String ean;
    private String manufacturerCode;
    private ProductDto product;

}
