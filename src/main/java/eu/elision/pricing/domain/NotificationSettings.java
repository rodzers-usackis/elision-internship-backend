package eu.elision.pricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents notification settings for a {@link ClientCompany}.
 * Contains {@link AlertRule}s.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean notifyViaEmail;

    @OneToMany
    private List<AlertRule> alertRules;

    @OneToOne
    private ClientCompany clientCompany;

}
