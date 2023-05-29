package eu.elision.pricing.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a scraping task.
 * Contains a list of product IDs whose prices should be scraped.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapingTaskDto {

    private List<UUID> productIds;

}
