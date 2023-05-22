package eu.elision.pricing.controller;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.service.ProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for {@link Product}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a new {@link Product}.
     *
     * @param user       the authenticated user
     * @param productDto the product to create
     * @return the created product
     */
    @CrossOrigin("http://localhost:3000")
    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@AuthenticationPrincipal User user,
                                                    @RequestBody ProductDto productDto) {

        productService.createProduct(productDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Returns all {@link Product}s.
     *
     * @return all products
     */
    @CrossOrigin("http://localhost:3000")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> products = productService.getAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Returns a {@link Product} by its UUID.
     *
     * @param uuid the UUID of the product
     * @return the product
     */
    @CrossOrigin("http://localhost:3000")
    @GetMapping("/products/{uuid}")
    public ResponseEntity<Product> getProduct(@PathVariable String uuid) {

        Product product = productService.getProduct(UUID.fromString(uuid));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Deletes a {@link Product} by its UUID.
     *
     * @param uuid the UUID of the product to delete
     * @return response with no content
     */
    @CrossOrigin("http://localhost:3000")
    @DeleteMapping("/products/{uuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String uuid) {

        productService.deleteProductById(UUID.fromString(uuid));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates a {@link Product}.
     *
     * @param productDto the product to update
     * @return the updated product
     */
    @CrossOrigin("http://localhost:3000")
    @PatchMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto) {

        Product product = productService.updateProduct(productDto);

        return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
    }
}
