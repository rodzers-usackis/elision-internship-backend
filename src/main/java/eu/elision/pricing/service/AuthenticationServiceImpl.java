package eu.elision.pricing.service;


import eu.elision.pricing.domain.Address;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AuthenticationRequest;
import eu.elision.pricing.dto.AuthenticationResponse;
import eu.elision.pricing.dto.RegistrationRequest;
import eu.elision.pricing.exceptions.EmailAlreadyRegistered;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link AuthenticationService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientCompanyRepository clientCompanyRepository;
    private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

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

        Optional<User> existingUser =
            userRepository.findByEmail(registrationRequest.getEmailAddress());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyRegistered("This email is already registered.");
        }

        Address address = Address.builder()
            .streetAddress(registrationRequest.getStreetAddress())
            .streetNumber(registrationRequest.getStreetNumber())
            .apartmentNumber("apartmentNumber")
            .city(registrationRequest.getCity())
            .country(registrationRequest.getCountry())
            .zipCode(registrationRequest.getZipCode())
            .build();

        ClientCompany clientCompany = ClientCompany.builder()
            .name(registrationRequest.getCompanyName())
            .VATNumber(registrationRequest.getVatNumber())
            .address(address)
            .website(registrationRequest.getCompanyWebsite())
            .categoriesProductsSold(registrationRequest.getProductCategory())
            .build();

        clientCompanyRepository.save(clientCompany);

        User user = User.builder()
            .firstName(registrationRequest.getFirstName())
            .lastName(registrationRequest.getLastName())
            .email(registrationRequest.getEmailAddress())
            .password(passwordEncoder.encode(registrationRequest.getPassword()))
            .clientCompany(clientCompany)
            .role(Role.CLIENT)
            .build();

        clientCompany.setUsers(List.of(user));

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
