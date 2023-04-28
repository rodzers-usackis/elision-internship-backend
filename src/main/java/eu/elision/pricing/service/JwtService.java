package eu.elision.pricing.service;

import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface for the JWT service.
 * Provides methods for extracting claims from a JWT,
 * generating a JWT from claims and validating a JWT.
 */
public interface JwtService {

    String extractUsername(String jwt);

    <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver);

    String generateToken(Map<String, Object> claims, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String jwt, UserDetails userDetails);

}
