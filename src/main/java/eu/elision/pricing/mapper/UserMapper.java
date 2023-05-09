package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.UserDto;

/**
 * Mapper for mapping {@link User} to {@link UserDto}.
 */
public interface UserMapper {
    UserDto domainToDto(User user);
}
