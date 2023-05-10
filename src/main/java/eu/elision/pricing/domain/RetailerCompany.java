package eu.elision.pricing.domain;
import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a retailer company whose products will be scraped and monitored.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RetailerCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String website;

    @ElementCollection(targetClass = ProductCategory.class)
    @Enumerated(EnumType.STRING)
    private Set<ProductCategory> categoriesProductsSold;
}
