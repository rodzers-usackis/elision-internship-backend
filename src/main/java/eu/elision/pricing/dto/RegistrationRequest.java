package eu.elision.pricing.dto;

import eu.elision.pricing.domain.ProductCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.util.Set;

/**
 * The registration request containing all the information displayed during the registration.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {


    @Length(min = 2, max = 100)
    private String companyName;

    @Length(min = 2, max = 100)
    private String vatNumber;

    @Length(min = 2, max = 100)
    private String companyWebsite;

    @Enumerated(EnumType.STRING)
    private Set<ProductCategory> productCategory;

    @Length(min = 2, max = 100)
    private String streetAddress;

    @Length(min = 2, max = 100)
    private String streetNumber;

    @Length(min = 2, max = 100)
    private String city;

    @Length(min = 2, max = 100)
    private String zipCode;

    @Length(min = 2, max = 100)
    private String country;

    @Length(min = 2, max = 100)
    private String firstName;

    @Length(min = 2, max = 100)
    private String lastName;

    @Email
    @Length(min = 5, max = 100)
    private String emailAddress;

    @Length(min = 6, max = 100)
    private String password;

}
