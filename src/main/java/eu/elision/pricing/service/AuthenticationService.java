package eu.elision.pricing.service;

import eu.elision.pricing.dto.AuthenticationRequest;
import eu.elision.pricing.dto.AuthenticationResponse;
import eu.elision.pricing.dto.RegistrationRequest;

/**
 * Interface for the authentication service.
 * Provides methods for registering and authenticating users.
 */
public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    AuthenticationResponse register(RegistrationRequest registrationRequest);

}
