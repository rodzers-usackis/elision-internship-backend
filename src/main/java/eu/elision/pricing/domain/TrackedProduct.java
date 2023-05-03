package eu.elision.pricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a product whose price will be tracked by the {@link ClientCompany}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrackedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    /**
     * Current price of the {@link Product} at the {@link ClientCompany}.
     */
    private double clientCurrentPrice;

    /**
     * The {@link Product} whose price is tracked by the {@link ClientCompany}.
     */
    @ManyToOne
    private Product product;

    /**
     * The {@link ClientCompany} that tracks the price of the {@link Product}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private ClientCompany clientCompany;

}
