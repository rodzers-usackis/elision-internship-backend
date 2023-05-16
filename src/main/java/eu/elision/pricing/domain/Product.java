package eu.elision.pricing.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a product whose price will be scraped and monitored.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String ean;

    @Column(nullable = false)
    private String manufacturerCode;

    @Column(nullable = false)
    private ProductCategory category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<AlertRule> alertRules;

    public Product(String name, String description, String ean, String manufacturerCode, ProductCategory productCategory) {
        this.name = name;
        this.description = description;
        this.ean = ean;
        this.manufacturerCode = manufacturerCode;
        this.category = productCategory;
    }
}
