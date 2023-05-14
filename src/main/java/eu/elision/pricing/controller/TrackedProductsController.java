package eu.elision.pricing.controller;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductDto;
import eu.elision.pricing.dto.TrackedProduct.TrackedProductPriceUpdateDto;
import eu.elision.pricing.mapper.TrackedProductMapper;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import eu.elision.pricing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TrackedProductsController {

    private final TrackedProductRepository trackedProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TrackedProductMapper trackedProductMapper;

    private ClientCompany getClientCompanyFromUserDetails(UserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        ClientCompany clientCompany = user.getClientCompany();

        if (clientCompany == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return clientCompany;
    }

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProduct> createTrackedProduct(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody TrackedProductDto trackedProductDto) {

        ClientCompany clientCompany = getClientCompanyFromUserDetails(userDetails);

        TrackedProduct trackedProduct = trackedProductMapper.dtoToDomain(trackedProductDto);

        if (trackedProduct.getProduct() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        clientCompany.getTrackedProducts().add(trackedProduct);

        trackedProductRepository.save(trackedProduct);

        return new ResponseEntity<>(trackedProduct, HttpStatus.CREATED);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/client-company/tracked-products")
    public ResponseEntity<List<TrackedProduct>> getTrackedProducts(@AuthenticationPrincipal UserDetails userDetails) {

        ClientCompany clientCompany = getClientCompanyFromUserDetails(userDetails);

        List<TrackedProduct> trackedProducts = trackedProductRepository.findTrackedProductByClientCompanyId(clientCompany.getId());

        return new ResponseEntity<>(trackedProducts, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @DeleteMapping("/client-company/tracked-products")
    public ResponseEntity<Void> deleteProducts(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<UUID> trackedProductIds) {

        ClientCompany clientCompany = getClientCompanyFromUserDetails(userDetails);

        trackedProductRepository.deleteTrackedProducts(clientCompany.getId(), trackedProductIds);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @PatchMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProduct> updateTrackedProduct(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody TrackedProductPriceUpdateDto trackedProductDto) {

        ClientCompany clientCompany = getClientCompanyFromUserDetails(userDetails);

        TrackedProduct trackedProduct = trackedProductRepository.findById(UUID.fromString(trackedProductDto.getId())).orElse(null);

        if (trackedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        trackedProduct.setProductSellPrice(trackedProductDto.getProductSellPrice());
        trackedProduct.setProductPurchaseCost(trackedProductDto.getProductPurchaseCost());
        trackedProduct.setTracked(trackedProductDto.isTracked());
        trackedProduct = trackedProductRepository.save(trackedProduct);

        return new ResponseEntity<>(trackedProduct, HttpStatus.OK);
    }
}
