package eu.elision.pricing.dto;

import eu.elision.pricing.domain.Role;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
