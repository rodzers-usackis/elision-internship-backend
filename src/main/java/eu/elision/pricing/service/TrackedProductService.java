package eu.elision.pricing.service;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductPriceUpdateDto;

import java.util.List;
import java.util.UUID;

public interface TrackedProductService {

    TrackedProduct createTrackedProductFromDto(User user, TrackedProductDto trackedProductDto);
    List<TrackedProduct> getTrackedProducts(User user);
    TrackedProduct updateTrackedProduct(User user, TrackedProductPriceUpdateDto trackedProductPriceUpdateDto);
    void deleteTrackedProducts(User user, List<UUID> trackedProductIds);
}
