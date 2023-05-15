package eu.elision.pricing.service;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductPriceUpdateDto;
import eu.elision.pricing.exceptions.BadRequestException;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrackedProductServiceImpl implements TrackedProductService {

    private final TrackedProductRepository trackedProductRepository;
    private final ProductRepository productRepository;
    private final ClientCompanyRepository clientCompanyRepository;

    @Override
    public TrackedProduct createTrackedProductFromDto(User user, TrackedProductDto trackedProductDto) {

        Product product;

        if (trackedProductDto.getProductEAN() != null) {
            product = productRepository.findByEan(trackedProductDto.getProductEAN());
        } else {
            product = productRepository.findById(UUID.fromString(trackedProductDto.getProductId())).orElse(null);
        }

        if (product == null) {
            throw new BadRequestException(String.format("Invalid product: ID=%s\nEAN=%s", trackedProductDto.getProductId(), trackedProductDto.getProductEAN()));
        }

        ClientCompany clientCompany = clientCompanyRepository.findById(UUID.fromString(trackedProductDto.getClientCompanyId())).orElse(null);

        TrackedProduct trackedProduct = TrackedProduct.builder()
                .productPurchaseCost(trackedProductDto.getProductPurchaseCost())
                .productSellPrice(trackedProductDto.getProductSellPrice())
                .isTracked(trackedProductDto.isTracked())
                .product(product)
                .clientCompany(clientCompany)
                .build();

        trackedProductRepository.save(trackedProduct);

        return trackedProduct;
    }

    @Override
    public List<TrackedProduct> getTrackedProducts(User user) {

        return trackedProductRepository.findTrackedProductByClientCompanyId(user.getClientCompany().getId());
    }

    @Transactional
    @Override
    public TrackedProduct updateTrackedProduct(User user, TrackedProductPriceUpdateDto trackedProductPriceUpdateDto) {

        TrackedProduct trackedProduct = trackedProductRepository.findById(UUID.fromString(trackedProductPriceUpdateDto.getId())).orElse(null);

        if (trackedProduct == null) {
            log.error("Tracked product with id {} not found", trackedProductPriceUpdateDto.getId());
            return null;
        }

        trackedProduct.setProductSellPrice(trackedProductPriceUpdateDto.getProductSellPrice());
        trackedProduct.setProductPurchaseCost(trackedProductPriceUpdateDto.getProductPurchaseCost());
        trackedProduct.setTracked(trackedProductPriceUpdateDto.isTracked());
        trackedProduct = trackedProductRepository.save(trackedProduct);

        return trackedProduct;
    }

    @Override
    public void deleteTrackedProducts(User user, List<UUID> trackedProductIds) {

        trackedProductRepository.deleteTrackedProducts(user.getClientCompany().getId(), trackedProductIds);
    }
}
