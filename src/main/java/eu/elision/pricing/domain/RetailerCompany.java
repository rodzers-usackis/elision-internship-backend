package eu.elision.pricing.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a retailer company whose products will be scraped and monitored.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RetailerCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String website;

    @ElementCollection(targetClass = ProductCategory.class)
    @Enumerated(EnumType.STRING)
    private Set<ProductCategory> categoriesProductsSold;


}
