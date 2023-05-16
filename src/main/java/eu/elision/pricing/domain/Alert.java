package eu.elision.pricing.domain;

import ch.qos.logback.core.net.server.Client;
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

    @ManyToOne
    private Price price;

    /**
     * The client company that owns this alert.
     */
    @ManyToOne
    private ClientCompany clientCompany;

    /**
     * The retailer company whose price change triggered the alert creation.
     */
    @ManyToOne
    private RetailerCompany retailerCompany;

    /**
     * The product whose price change triggered the alert creation.
     */
    @ManyToOne
    private Product product;

}
