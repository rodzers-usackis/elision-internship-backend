package eu.elision.pricing.dto;

import eu.elision.pricing.domain.Product;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link Product}.
 */
@Data
@Builder
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private String ean;
    private String manufacturerCode;
    private String category;
}
