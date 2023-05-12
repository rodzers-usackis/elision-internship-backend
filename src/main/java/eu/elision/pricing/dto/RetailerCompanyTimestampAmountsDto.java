package eu.elision.pricing.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for price history data.
 * Contains price values with timestamps for a single Company.
 *
 * @see PriceHistoryDto
 */
@Data
@Builder
public class RetailerCompanyTimestampAmountsDto {
    private RetailerCompanyDto retailerCompanyDto;
    private List<TimestampAmountDto> timestampAmounts;
}
