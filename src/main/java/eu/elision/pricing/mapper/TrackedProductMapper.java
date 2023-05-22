package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.trackedproduct.TrackedProductDto;
import eu.elision.pricing.dto.trackedproduct.TrackedProductWithDetailsDto;
import java.util.List;

/**
 * Mapper for mapping {@link TrackedProduct} to {@link TrackedProductDto}.
 */
public interface TrackedProductMapper {

    TrackedProductDto domainToDto(TrackedProduct trackedProduct);

    List<TrackedProductDto> domainsToDtos(List<TrackedProduct> trackedProducts);

    TrackedProductWithDetailsDto domainToDtoWithDetails(TrackedProduct trackedProduct);
}
