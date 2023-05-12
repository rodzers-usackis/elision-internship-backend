package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;

import java.util.List;

public interface TrackedProductMapper {

    TrackedProductDto domainToDto(TrackedProduct trackedProduct);
    List<TrackedProductDto> domainsToDtos(List<TrackedProduct> trackedProducts);
    TrackedProduct dtoToDomain(TrackedProductDto trackedProductDTO);
    List<TrackedProduct> dtosToDomains(List<TrackedProductDto> trackedProductDTOs);
}
