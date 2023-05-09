package eu.elision.pricing.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import eu.elision.pricing.domain.Product;

/**
 * DTO for price history data for
 * a single {@link Product}
 * Contains a list of {@link CompanyTimestampAmountsDto}.
 */
@Data
@Builder
public class PriceHistoryDto {

    private ProductDto product;
    private List<CompanyTimestampAmountsDto> data;

}
