package eu.elision.pricing.controller;

import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductPriceUpdateDto;
import eu.elision.pricing.service.TrackedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TrackedProductsController {

    private final TrackedProductService trackedProductService;

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProduct> createTrackedProduct(
            @AuthenticationPrincipal User user, @RequestBody TrackedProductDto trackedProductDto) {

        TrackedProduct savedTrackedProduct = trackedProductService.createTrackedProductFromDto(user, trackedProductDto);

        return new ResponseEntity<>(savedTrackedProduct, HttpStatus.CREATED);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/client-company/tracked-products")
    public ResponseEntity<List<TrackedProduct>> getTrackedProducts(@AuthenticationPrincipal User user) {

        List<TrackedProduct> trackedProducts = trackedProductService.getTrackedProducts(user);

        return new ResponseEntity<>(trackedProducts, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @DeleteMapping("/client-company/tracked-products")
    public ResponseEntity<Void> deleteProducts(@AuthenticationPrincipal User user, @RequestBody List<UUID> trackedProductIds) {

        trackedProductService.deleteTrackedProducts(user, trackedProductIds);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @PatchMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProduct> updateTrackedProduct(
            @AuthenticationPrincipal User user, @RequestBody TrackedProductPriceUpdateDto trackedProductPriceUpdateDto) {

        TrackedProduct trackedProduct = trackedProductService.updateTrackedProduct(user, trackedProductPriceUpdateDto);

        return new ResponseEntity<>(trackedProduct, HttpStatus.OK);
    }
}
