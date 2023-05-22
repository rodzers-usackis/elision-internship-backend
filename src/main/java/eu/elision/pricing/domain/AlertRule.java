package eu.elision.pricing.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Represents a rule that will be used to generate Alerts
 * after a {@link Product} price change.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AlertRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double price;

    @Enumerated(EnumType.STRING)
    private PriceComparisonType priceComparisonType;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private Product product;

    /**
     * The {@link RetailerCompany}s that will be tracked.
     * If empty, all {@link RetailerCompany}s will be tracked.
     * If not empty, only the specified {@link RetailerCompany}s will be tracked.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany
    @JoinTable(
            name = "alert_rule_retailer_company",
            joinColumns = @JoinColumn(name = "alert_rule_id"),
            inverseJoinColumns = @JoinColumn(name = "retailer_company_id")
    )
    private List<RetailerCompany> retailerCompanies;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "alert_settings_id", nullable = false)
    private AlertSettings alertSettings;


}
