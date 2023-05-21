package eu.elision.pricing.domain;

import jakarta.persistence.CascadeType;
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
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Represents notification settings for a {@link ClientCompany}.
 * Contains {@link AlertRule}s.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AlertSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean alertsActive;

    private boolean notifyViaEmail;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alertSettings")
    private List<AlertRule> alertRules;

    @OneToOne
    private ClientCompany clientCompany;

}
