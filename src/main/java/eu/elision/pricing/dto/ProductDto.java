package eu.elision.pricing.dto;

import eu.elision.pricing.domain.ProductCategory;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private String ean;
    private String manufacturerCode;
    private ProductCategory category;
}
