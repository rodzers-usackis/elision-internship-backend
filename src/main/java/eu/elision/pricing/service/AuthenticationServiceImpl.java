package eu.elision.pricing.service;


import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AuthenticationRequest;
import eu.elision.pricing.dto.AuthenticationResponse;
import eu.elision.pricing.dto.RegistrationRequest;
import eu.elision.pricing.exception.EmailAlreadyRegistered;
import eu.elision.pricing.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Implementation of {@link AuthenticationService}.
 */
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Saves the user in the {@link UserRepository} and returns a {@link AuthenticationResponse}
     * containing the JWT.
     *
     * @param registrationRequest the {@link RegistrationRequest}
     *                            containing the user's email and password
     * @return the {@link AuthenticationResponse} containing the JWT
     * @throws EmailAlreadyRegistered if the email is already registered
     */
    @Override
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        User user = new User(registrationRequest.getFirstName(), registrationRequest.getLastName(), registrationRequest.getEmail(),
            passwordEncoder.encode(registrationRequest.getPassword()));

        logger.debug("Registering user: {}", user);

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyRegistered("This email is already registered.");
        }

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }

    /**
     * Authenticates the user and returns a {@link AuthenticationResponse} containing the JWT.
     * The user is authenticated using the {@link AuthenticationManager}.
     * If the authentication is successful, the user is loaded into the Spring Security context.
     *
     * @param authenticationRequest the {@link AuthenticationRequest}
     *                              containing the user's email and password
     * @return the {@link AuthenticationResponse} containing the JWT
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));

        User user = userRepository.findByEmail(authenticationRequest.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }
}
