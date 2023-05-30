package eu.elision.pricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SuggestedPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double suggestedPrice;

    @ManyToOne
    private Product product;

    private LocalDateTime timestamp;


}
