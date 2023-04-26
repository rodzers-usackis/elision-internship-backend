package eu.elision.pricing.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @Length(min = 3, max = 100)
    private String firstName;

    @Length(min = 3, max = 100)
    private String lastName;

    @Email
    @Length(min = 5, max = 100)
    private String email;

    @Length(min = 6, max = 100)
    private String password;

}
