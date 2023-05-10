package eu.elision.pricing.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private String ean;
    private String manufacturerCode;
    private String category;
}
