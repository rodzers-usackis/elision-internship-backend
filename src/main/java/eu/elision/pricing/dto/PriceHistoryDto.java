package eu.elision.pricing.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for price history data for
 * a single product and company.
 * Contains a list of {@link TimestampAmountDto}.
 */
@Data
@Builder
public class PriceHistoryDto {

    private ProductDto product;
    private CompanyDto company;
    private List<TimestampAmountDto> data;

}
