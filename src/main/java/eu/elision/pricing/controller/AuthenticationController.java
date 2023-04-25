package eu.elision.pricing.controller;

import eu.elision.pricing.dto.AuthenticationRequest;
import eu.elision.pricing.dto.AuthenticationResponse;
import eu.elision.pricing.dto.RegistrationRequest;
import eu.elision.pricing.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegistrationRequest registrationRequest
    ) {
        return ResponseEntity.ok(authenticationService.register(registrationRequest));
    }

}
