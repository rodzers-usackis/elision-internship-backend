package eu.elision.pricing.dto;

import eu.elision.pricing.domain.Product;
import java.util.List;
import lombok.Builder;
import lombok.Data;

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
