package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link UserMapper}.
 */
@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto domainToDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }
}
