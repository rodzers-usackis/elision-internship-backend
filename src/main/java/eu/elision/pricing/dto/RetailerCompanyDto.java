package eu.elision.pricing.dto;

import eu.elision.pricing.domain.RetailerCompany;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link RetailerCompany}.
 */
@Data
@Builder
public class RetailerCompanyDto {
    private UUID id;
    private String name;
}
