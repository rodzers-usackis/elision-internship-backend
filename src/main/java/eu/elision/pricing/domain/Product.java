package eu.elision.pricing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a product whose price will be scraped and monitored.
 */
@Data
@Builder
@EqualsAndHashCode
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<AlertRule> alertRules;

    /**
     * Constructor for Product.
     *
     * @param name             The name of the product.
     * @param description      The description of the product.
     * @param ean              The EAN code of the product.
     * @param manufacturerCode The manufacturer code of the product.
     * @param productCategory  The category of the product.
     */
    public Product(String name,
                   String description,
                   String ean,
                   String manufacturerCode,
                   ProductCategory productCategory
    ) {
        this.name = name;
        this.description = description;
        this.ean = ean;
        this.manufacturerCode = manufacturerCode;
        this.category = productCategory;
    }
}
