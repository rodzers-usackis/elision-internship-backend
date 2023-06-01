package eu.elision.pricing.events;

import eu.elision.pricing.domain.SuggestedPrice;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionsMadeEvent {

    private List<SuggestedPrice> suggestedPrices;

}
