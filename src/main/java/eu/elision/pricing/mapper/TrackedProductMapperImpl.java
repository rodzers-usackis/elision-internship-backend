package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TrackedProductMapperImpl implements TrackedProductMapper {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

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

    @Override
    public TrackedProduct dtoToDomain(TrackedProductDto trackedProductDto) {
        Product product;

        if (trackedProductDto.getProductEAN() != null) {
            product = productRepository.findByEan(trackedProductDto.getProductEAN());
        } else {
            product = productRepository.findById(UUID.fromString(trackedProductDto.getProductId())).orElse(null);
        }

        return TrackedProduct.builder()
                .productPurchaseCost(trackedProductDto.getProductPurchaseCost())
                .productSellPrice(trackedProductDto.getProductSellPrice())
                .isTracked(trackedProductDto.isTracked())
                .product(product)
                .clientCompany(clientCompanyRepository.findById(UUID.fromString(trackedProductDto.getClientCompanyId())).orElse(null))
                .build();
    }

    @Override
    public List<TrackedProduct> dtosToDomains(List<TrackedProductDto> trackedProductDTOs) {
        return trackedProductDTOs.stream().map(this::dtoToDomain).collect(Collectors.toList());
    }
}
