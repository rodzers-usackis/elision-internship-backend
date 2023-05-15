package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrackedProductMapperImpl implements TrackedProductMapper {

    @Override
    public TrackedProductDto domainToDto(TrackedProduct trackedProduct) {
        return TrackedProductDto.builder()
                .id(String.valueOf(trackedProduct.getId()))
                .productPurchaseCost(trackedProduct.getProductPurchaseCost())
                .productSellPrice(trackedProduct.getProductSellPrice())
                .isTracked(trackedProduct.isTracked())
                .productId(String.valueOf(trackedProduct.getProduct().getId()))
                .clientCompanyId(String.valueOf(trackedProduct.getClientCompany().getId()))
                .build();
    }

    @Override
    public List<TrackedProductDto> domainsToDtos(List<TrackedProduct> trackedProducts) {
        return trackedProducts.stream().map(this::domainToDto).collect(Collectors.toList());
    }
}
