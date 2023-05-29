package eu.elision.pricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an alert message
 * that will be shown to the client
 * when a {@link Price} of a {@link Product}
 * fulfills the {@link AlertRule} conditions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean read;

    private LocalDateTime timestamp;

    private double price;

    /**
     * The price threshold that was set for the alert rule
     * that triggered the alert creation.
     */
    private double alertRulePriceThreshold;

    /**
     * The price comparison type that was set for the alert rule.
     */
    private PriceComparisonType priceComparisonType;

    /**
     * The user that owns this alert.
     */
    @ManyToOne(optional = false)
    private User user;

    /**
     * The retailer company whose price change triggered the alert creation.
     */
    @ManyToOne(optional = false)
    private RetailerCompany retailerCompany;

    /**
     * The product whose price change triggered the alert creation.
     */
    @ManyToOne(optional = false)
    private Product product;

}
