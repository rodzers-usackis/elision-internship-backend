package eu.elision.pricing.dto;

import eu.elision.pricing.domain.ClientCompany;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link ClientCompany}.
 */
@Data
@Builder
public class ClientCompanyDto {

    private String address;
    private List<String> trackedProducts;
    private List<String> users;
}
