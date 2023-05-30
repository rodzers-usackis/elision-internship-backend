package eu.elision.pricing.service;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.trackedproduct.TrackedProductDto;
import eu.elision.pricing.dto.trackedproduct.TrackedProductPriceUpdateDto;
import eu.elision.pricing.dto.trackedproduct.TrackedProductWithDetailsDto;
import eu.elision.pricing.exceptions.NotFoundException;
import eu.elision.pricing.mapper.TrackedProductMapper;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link TrackedProductService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TrackedProductServiceImpl implements TrackedProductService {

    private final TrackedProductRepository trackedProductRepository;
    private final TrackedProductMapper trackedProductMapper;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public TrackedProduct createTrackedProductFromDto(User user,
                                                      TrackedProductDto trackedProductDto) {

        Product product;

        if (trackedProductDto.getEan() != null && !trackedProductDto.getEan().isEmpty()) {
            log.debug(">>> Creating TP by EAN: {}", trackedProductDto.getEan());
            product = productRepository.findByEan(trackedProductDto.getEan());
        } else {
            log.debug(">>> Creating TP by manufacturer code: {}",
                trackedProductDto.getManufacturerCode());
            product =
                productRepository.findByManufacturerCode(trackedProductDto.getManufacturerCode());
        }

        if (product == null) {
            throw new NotFoundException(
                String.format("Invalid product: Manufacturer code=%s\nEAN=%s",
                    trackedProductDto.getManufacturerCode(), trackedProductDto.getEan()));
        }

        //        ClientCompany clientCompany = clientCompanyRepository.findById(UUID
        //        .fromString(trackedProductDto.getClientCompanyId())).orElse(null);

        ClientCompany clientCompany = user.getClientCompany();
        log.debug(">>> Creating TP for client company: {}", user.getClientCompany().getId());

        TrackedProduct trackedProduct = TrackedProduct.builder()
            .productPurchaseCost(trackedProductDto.getProductPurchaseCost())
            .productSellPrice(trackedProductDto.getProductSellPrice())
            .minPrice(trackedProductDto.getMinPrice())
            .isTracked(true)
            .product(product)
            .clientCompany(clientCompany)
            .build();

        log.debug(">>> Creating TP: {}", trackedProduct);

        trackedProductRepository.save(trackedProduct);

        log.debug(">>> Created TP: {}", trackedProduct);

        return trackedProduct;
    }

    @Override
    public List<TrackedProductWithDetailsDto> getTrackedProducts(User user) {

        List<TrackedProduct> trackedProducts =
            trackedProductRepository.findTrackedProductByClientCompanyId(
                user.getClientCompany().getId());

        return trackedProducts.stream().map(trackedProductMapper::domainToDtoWithDetails)
            .toList();
    }

    @Transactional
    @Override
    public TrackedProduct updateTrackedProduct(
        User user,
        TrackedProductPriceUpdateDto trackedProductPriceUpdateDto) {

        TrackedProduct trackedProduct =
            trackedProductRepository.findById(UUID.fromString(trackedProductPriceUpdateDto.getId()))
                .orElse(null);

        if (trackedProduct == null) {
            throw new NotFoundException(String.format("Invalid tracked product: ID=%s",
                trackedProductPriceUpdateDto.getId()));
        }

        trackedProduct.setProductSellPrice(trackedProductPriceUpdateDto.getProductSellPrice());
        trackedProduct.setProductPurchaseCost(
            trackedProductPriceUpdateDto.getProductPurchaseCost());
        trackedProduct.setTracked(trackedProductPriceUpdateDto.isTracked());
        trackedProduct.setMinPrice(trackedProductPriceUpdateDto.getMinPrice());
        trackedProduct = trackedProductRepository.save(trackedProduct);

        return trackedProduct;
    }

    @Override
    public void deleteTrackedProducts(User user, List<UUID> trackedProductIds) {

        trackedProductRepository.deleteTrackedProducts(user.getClientCompany().getId(),
            trackedProductIds);
    }

    @Override
    public List<TrackedProduct> getTrackedProductsByProductId(UUID productId) {
        return trackedProductRepository.findTrackedProductsByProduct_Id(productId);
    }
}
