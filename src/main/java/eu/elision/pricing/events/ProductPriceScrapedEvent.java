package eu.elision.pricing.events;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
@Builder
@AllArgsConstructor
public class ProductPriceScrapedEvent {

    private Product product;
    private List<Price> newPrices;


}
