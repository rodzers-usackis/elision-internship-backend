package eu.elision.pricing.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a client company that will track prices of chosen {@link Product}s.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ClientCompany extends RetailerCompany {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clientCompany")
    private List<TrackedProduct> trackedProducts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clientCompany")
    private List<User> users;
}
