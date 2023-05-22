package eu.elision.pricing.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Represents notification settings for each {@link User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AlertSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private boolean emailNotifications;

    @Column(nullable = false)
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private AlertStorageDuration alertStorageDuration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
