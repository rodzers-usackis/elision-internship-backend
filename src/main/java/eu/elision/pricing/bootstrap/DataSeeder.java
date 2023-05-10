package eu.elision.pricing.bootstrap;

import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class is used to seed the database with some initial data.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("seed-data")
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User user = User.builder()
            .email("test@elision.eu")
            .password(passwordEncoder.encode("secure_password"))
            .firstName("John")
            .lastName("Smith")
            .role(Role.ADMIN)
            .build();

        userRepository.save(user);
    }
}
