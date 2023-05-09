package eu.elision.pricing.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for a single timestamp and price amount.
 */
@Data
@Builder
public class TimestampAmountDto {

            private LocalDateTime timestamp;
            private double amount;
}
