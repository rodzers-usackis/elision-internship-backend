package eu.elision.pricing.controller;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.TrackedProductDto;
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
public class ClientCompanyController {

    private final TrackedProductRepository trackedProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TrackedProductMapper trackedProductMapper;

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProduct> createTrackedProduct(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody TrackedProductDto trackedProductDto) {

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

        TrackedProduct trackedProduct = trackedProductMapper.dtoToDomain(trackedProductDto);

        if (productRepository.findById(UUID.fromString(trackedProductDto.getProduct())).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        clientCompany.getTrackedProducts().add(trackedProduct);

        trackedProductRepository.save(trackedProduct);

        return new ResponseEntity<>(trackedProduct, HttpStatus.CREATED);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/client-company/tracked-products")
    public ResponseEntity<List<TrackedProduct>> getTrackedProducts(@AuthenticationPrincipal UserDetails userDetails) {

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

        List<TrackedProduct> trackedProducts = trackedProductRepository.findTrackedProductByClientCompanyId(clientCompany.getId());

        return new ResponseEntity<>(trackedProducts, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @DeleteMapping("/client-company/tracked-products")
    public ResponseEntity<Void> deleteProducts(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<UUID> trackedProductIds) {

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

        trackedProductRepository.deleteTrackedProducts(clientCompany.getId(), trackedProductIds);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @PutMapping("/client-company/tracked-products")
    public ResponseEntity<TrackedProduct> updateTrackedProduct(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody TrackedProductDto trackedProductDto) {

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

        TrackedProduct trackedProduct = trackedProductMapper.dtoToDomain(trackedProductDto);

        if (trackedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        trackedProduct.setId(UUID.fromString(trackedProductDto.getId()));
        trackedProduct = trackedProductRepository.save(trackedProduct);

        return new ResponseEntity<>(trackedProduct, HttpStatus.OK);
    }
}
