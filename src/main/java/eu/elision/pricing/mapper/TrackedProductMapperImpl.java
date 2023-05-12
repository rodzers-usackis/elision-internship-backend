package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.TrackedProductDto;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrackedProductMapperImpl implements TrackedProductMapper {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public TrackedProductDto domainToDto(TrackedProduct trackedProduct) {
        return TrackedProductDto.builder()
                .id(String.valueOf(trackedProduct.getId()))
                .productPurchaseCost(trackedProduct.getProductPurchaseCost())
                .productSellPrice(trackedProduct.getProductSellPrice())
                .product(String.valueOf(trackedProduct.getProduct().getId()))
                .clientCompany(String.valueOf(trackedProduct.getClientCompany().getId()))
                .build();
    }

    @Override
    public TrackedProduct dtoToDomain(TrackedProductDto trackedProductDto) {
        Product product = productRepository.findById(UUID.fromString(trackedProductDto.getProduct())).orElse(null);

        return TrackedProduct.builder()
                .id(UUID.fromString(trackedProductDto.getId()))
                .productPurchaseCost(trackedProductDto.getProductPurchaseCost())
                .productSellPrice(trackedProductDto.getProductSellPrice())
                .product(product)
                .build();
    }
}
