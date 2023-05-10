package eu.elision.pricing.controller;

import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.UserDto;
import eu.elision.pricing.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for {@link User}.
 */
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserRestController {

    private final UserMapper userMapper;


    @GetMapping("/me")
    public ResponseEntity<UserDto> getAuthenticatedUsersInformation(
        @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.domainToDto(user));
    }
}
