package eu.elision.pricing.security;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configures the security for the application, such as authentication and authorization rules.
 * This class specifies the beans for PasswordEncoder,
 * AuthenticationProvider, and AuthenticationManager,
 * and defines the security filter chain used for authenticating requests.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;


    /**
     * {@link Bean} for security filter chain.
     * This method configures the security filter chain.
     * {@link JwtAuthenticationFilter} is added to the filter chain.
     * The filter chain is configured to authenticate all requests
     * except for the authentication endpoint,
     * and '/'.
     * The CSRF protection is disabled, and the session management is set to stateless.
     * The authentication provider is set to the autowired {@link AuthenticationProvider}.
     *
     * @param http                   the HTTP Security which will be used
     *                               to build the security filter chain
     * @param authenticationProvider the authentication provider used to authenticate requests
     * @return configured {@link SecurityFilterChain}
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authenticationProvider)
        throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/api/auth/**")
            .permitAll()
            .requestMatchers(toH2Console())
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .headers().frameOptions().sameOrigin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * {@link Bean} for the authentication provider used to authenticate requests.
     *
     * @return {@link DaoAuthenticationProvider}
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * {@link Bean} for the authentication manager.
     *
     * @param configuration the authentication configuration
     * @return authentication manager from {@link AuthenticationConfiguration}
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * {@link Bean} for the password encoder.
     *
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
