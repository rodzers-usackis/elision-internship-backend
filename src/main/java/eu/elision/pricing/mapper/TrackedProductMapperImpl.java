package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductWithDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TrackedProductMapperImpl implements TrackedProductMapper {

    private final ProductMapper productMapper;

    @Override
    public TrackedProductDto domainToDto(TrackedProduct trackedProduct) {
        return TrackedProductDto.builder()
            .id(String.valueOf(trackedProduct.getId()))
            .productPurchaseCost(trackedProduct.getProductPurchaseCost())
            .productSellPrice(trackedProduct.getProductSellPrice())
            .isTracked(trackedProduct.isTracked())
            .build();
    }

    @Override
    public List<TrackedProductDto> domainsToDtos(List<TrackedProduct> trackedProducts) {
        return trackedProducts.stream().map(this::domainToDto).collect(Collectors.toList());
    }

    @Override
    public TrackedProductWithDetailsDto domainToDtoWithDetails(TrackedProduct trackedProduct) {
        return TrackedProductWithDetailsDto.builder()
            .id(String.valueOf(trackedProduct.getId()))
            .product(productMapper.domainToDto(trackedProduct.getProduct()))
            .isTracked(trackedProduct.isTracked())
            .productSellPrice(trackedProduct.getProductSellPrice())
            .productPurchaseCost(trackedProduct.getProductPurchaseCost())
            .ean(trackedProduct.getProduct().getEan())
            .manufacturerCode(trackedProduct.getProduct().getManufacturerCode())
            .build();
    }
}
