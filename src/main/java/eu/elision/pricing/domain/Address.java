package eu.elision.pricing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

/**
 * Postal address of a {@link ClientCompany}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String streetNumber;

    private String apartmentNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    @JsonIgnore
    @OneToOne(mappedBy = "address", optional = false)
    private ClientCompany clientCompany;
}
