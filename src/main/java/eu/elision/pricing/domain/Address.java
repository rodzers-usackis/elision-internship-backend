package eu.elision.pricing.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Postal address of a {@link ClientCompany}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String streetNumber;
    private String apartmentNumber;
    private String city;
    private String postalCode;
    private String country;

}
