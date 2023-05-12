package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.TrackedProductDto;

public interface TrackedProductMapper {

    TrackedProductDto domainToDto(TrackedProduct trackedProduct);
    TrackedProduct dtoToDomain(TrackedProductDto trackedProductDTO);
}
