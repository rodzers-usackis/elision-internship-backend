package eu.elision.pricing.controller;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.trackedproduct.TrackedProductDto;
import eu.elision.pricing.dto.trackedproduct.TrackedProductPriceUpdateDto;
import eu.elision.pricing.dto.trackedproduct.TrackedProductWithDetailsDto;
import eu.elision.pricing.mapper.TrackedProductMapper;
import eu.elision.pricing.service.TrackedProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for {@link TrackedProduct}s.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TrackedProductsController {

    private final TrackedProductService trackedProductService;
    private final TrackedProductMapper trackedProductMapper;

    /**
     * Creates a new {@link TrackedProduct} from the given {@link TrackedProductDto}.
     *
     * @param user              the authenticated user
     * @param trackedProductDto the {@link TrackedProductDto} to create the {@link TrackedProduct}
     * @return {@link ResponseEntity} with the {@link TrackedProductWithDetailsDto}
     *     of the created {@link TrackedProduct}
     */
    @CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
    @PostMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProductWithDetailsDto> createTrackedProduct(
        @AuthenticationPrincipal User user, @RequestBody TrackedProductDto trackedProductDto) {

        TrackedProduct savedTrackedProduct =
            trackedProductService.createTrackedProductFromDto(user, trackedProductDto);

        TrackedProductWithDetailsDto trackedProductWithDetailsDto =
            trackedProductMapper.domainToDtoWithDetails(savedTrackedProduct);
        return new ResponseEntity<>(trackedProductWithDetailsDto, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
    @GetMapping("/client-company/tracked-products")
    public ResponseEntity<List<TrackedProductWithDetailsDto>> getTrackedProducts(
        @AuthenticationPrincipal User user) {

        List<TrackedProductWithDetailsDto> trackedProducts =
            trackedProductService.getTrackedProducts(user);

        return new ResponseEntity<>(trackedProducts, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
    @DeleteMapping("/client-company/tracked-products")
    public ResponseEntity<Void> deleteProducts(@AuthenticationPrincipal User user,
                                               @RequestBody List<UUID> trackedProductIds) {

        trackedProductService.deleteTrackedProducts(user, trackedProductIds);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
    @PatchMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProduct> updateTrackedProduct(
        @AuthenticationPrincipal User user,
        @RequestBody TrackedProductPriceUpdateDto trackedProductPriceUpdateDto) {

        TrackedProduct trackedProduct =
            trackedProductService.updateTrackedProduct(user, trackedProductPriceUpdateDto);

        //TODO: map to dto
        return new ResponseEntity<>(trackedProduct, HttpStatus.OK);
    }
}
