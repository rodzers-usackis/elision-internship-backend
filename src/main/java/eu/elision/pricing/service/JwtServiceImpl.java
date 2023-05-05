package eu.elision.pricing.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link JwtService} interface.
 */
@Service
public class JwtServiceImpl implements JwtService {

    private static final byte[] SECRET_KEY =
        Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();


    /**
     * Extracts the username from the JWT.
     *
     * @param jwt the JWT
     * @return the username extracted from the JWT
     */
    @Override
    public String extractUsername(String jwt) {
        System.out.println(Encoders.BASE64.encode(SECRET_KEY));
        return extractClaim(jwt, Claims::getSubject);
    }

    /**
     * Checks if the JWT is valid.
     *
     * @param jwt         the JWT
     * @param userDetails the user details that should correspond
     *                    to the user details in the JWT
     * @return true if the JWT is valid, false otherwise
     */
    @Override
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    /**
     * Checks if the JWT is expired.
     *
     * @param jwt the JWT that should be checked
     * @return true if the JWT is expired, false otherwise
     */
    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT.
     *
     * @param jwt the JWT from which the expiration date should be extracted
     * @return the expiration date extracted from the JWT
     */
    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    /**
     * Generates a JWT from the user details.
     *
     * @param userDetails user details of the user for whom the JWT should be generated
     * @return the generated JWT
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT from the claims and user details.
     *
     * @param claims      additional claims that can be included in the JWT
     * @param userDetails user details of the user for whom the JWT should be generated
     * @return the generated JWT
     */
    @Override
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();

    }

    /**
     * Extracts a claim from the JWT.
     *
     * @param jwt            the JWT from which the claim should be extracted
     * @param claimsResolver the claims resolver function
     *                       that will be used to extract a specific claim
     * @param <T>            the type of the claim
     * @return the extracted claim
     */
    @Override
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(jwt)
            .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY);
    }
}
