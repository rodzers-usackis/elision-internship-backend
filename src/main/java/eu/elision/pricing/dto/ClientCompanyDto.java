package eu.elision.pricing.dto;

import eu.elision.pricing.domain.ClientCompany;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
