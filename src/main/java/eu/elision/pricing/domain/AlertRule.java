package eu.elision.pricing.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a rule that will be used to generate Alerts
 * after a {@link Product} price change.
 */
@Data
@Builder
@NoArgsConstructor
@Entity
public class AlertRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double price;

    @Enumerated(EnumType.STRING)
    private PriceComparisonType priceComparisonType;

    @ManyToOne
    private Product product;

    /**
     * The {@link RetailerCompany}s that will be tracked.
     * If empty, all {@link RetailerCompany}s will be tracked.
     * If not empty, only the specified {@link RetailerCompany}s will be tracked.
     */
    @ManyToMany
    private List<RetailerCompany> retailerCompanies;

}
