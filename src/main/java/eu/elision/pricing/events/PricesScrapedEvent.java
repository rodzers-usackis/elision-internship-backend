package eu.elision.pricing.events;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PricesScrapedEvent {

    Map<Product, List<Price>> productToPricesMap;

}
