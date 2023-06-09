package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.PriceHistoryDto;
import java.util.List;

/**
 * Mapper for mapping {@link Price}s to {@link PriceHistoryDto}.
 */
public interface PriceHistoryMapper {
    /**
     * Mapping multiple {@link Price}s into a {@link PriceHistoryDto}.
     *
     * @param prices list of {@link Price}s all for the same {@link Product}
     * @return {@link PriceHistoryDto} containing the values of the {@link Price}s
     */
    PriceHistoryDto domainToDto(List<Price> prices);
}
