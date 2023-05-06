package eu.elision.pricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the price of a {@link Product} of a {@link RetailerCompany}
 * at a certain point in time.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime timestamp;

    /**
     * The price of the product.
     */
    private double amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "retailer_company_id")
    private RetailerCompany retailerCompany;

}
