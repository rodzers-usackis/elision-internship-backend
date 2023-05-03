package eu.elision.pricing.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Holds the information necessary to scrape the price of a {@link Product}
 * in a {@link RetailerCompany}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PriceScrapingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String cssSelector;

    @Column(nullable = false)
    private String url;

    /**
     * Whether the scraping configuration is active or not.
     * If it is not active, this configuration will not be used to scrape prices.
     */
    private boolean active;

    /**
     * The company whose price will be scraped using this configuration.
     */
    @ManyToOne
    @JoinColumn(name = "retailer_company_id", nullable = false)
    private RetailerCompany retailerCompany;

    /**
     * The product whose price will be scraped using this configuration.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
