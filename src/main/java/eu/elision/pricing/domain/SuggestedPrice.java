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
import lombok.ToString;

/**
 * The suggested price for a product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SuggestedPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double suggestedPrice;

    @ManyToOne(optional = false)
    private Product product;

    private LocalDateTime timestamp;

    @ToString.Exclude
    @ManyToOne(optional = false)
    private ClientCompany clientCompany;


}
