package eu.elision.pricing.bootstrap;

import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
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
