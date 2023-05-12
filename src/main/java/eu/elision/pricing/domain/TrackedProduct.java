package eu.elision.pricing.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.UUID;

import lombok.*;

/**
 * Represents a product whose price will be tracked by the {@link ClientCompany}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrackedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Purchase costs of the {@link Product} at the {@link ClientCompany}.
     */
    @Column(nullable = false)
    private double productPurchaseCost;

    /**
     * Selling price of the {@link Product} at the {@link ClientCompany}.
     */
    @Column(nullable = false)
    private double productSellPrice;

    /**
     * The {@link Product} whose price is tracked by the {@link ClientCompany}.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The {@link ClientCompany} that tracks the price of the {@link Product}.
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id")
    private ClientCompany clientCompany;
}
