package eu.elision.pricing.controller;

import eu.elision.pricing.dto.AuthenticationRequest;
import eu.elision.pricing.dto.AuthenticationResponse;
import eu.elision.pricing.dto.RegistrationRequest;
import eu.elision.pricing.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for authentication REST endpoints.
 * This class defines the REST endpoints for authentication and registration.
 *
 * @see RegistrationExceptionHandler
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    /**
     * Handles the request to authenticate the user.
     *
     * @return the {@link ResponseEntity} with the HTTP status code 200,
     *     and containing the {@link AuthenticationRequest}
     *     with the JWT token
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    /**
     * Handles the request to register the user.
     *
     * @param registrationRequest the request containing the user's email and password
     * @return the {@link ResponseEntity} with HTTP status code 201,
     *     and containing the {@link AuthenticationResponse}  with the JWT token
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegistrationRequest registrationRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authenticationService.register(registrationRequest));
    }

}
