package eu.elision.pricing.repository;

import eu.elision.pricing.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User>  findAllByAlertSettings_NotifyViaEmailTrue();

}
