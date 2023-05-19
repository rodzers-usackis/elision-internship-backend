package eu.elision.pricing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
