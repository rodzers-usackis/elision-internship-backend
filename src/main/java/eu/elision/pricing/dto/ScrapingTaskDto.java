package eu.elision.pricing.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for a scraping task.
 * Contains a list of product IDs whose prices should be scraped.
 */
@Data
@Builder
public class ScrapingTaskDto {

    private List<UUID> productIds;

}
