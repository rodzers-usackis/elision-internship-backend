package eu.elision.pricing.presentation;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.dto.ProductDTO;
import eu.elision.pricing.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CrossOrigin
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getEan(),
                productDTO.getManufacturerCode(),
                ProductCategory.valueOf(productDTO.getCategory())
        );

        productRepository.save(product);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();

        logger.debug("products: {}", products);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/products/{uuid}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String uuid) {
        Product product = productRepository.findById(UUID.fromString(uuid)).orElse(null);

        logger.debug("product: {}", product);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setEan(product.getEan());
        productDTO.setManufacturerCode(product.getManufacturerCode());
        productDTO.setCategory(product.getCategory().getCategory());

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/products/{uuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String uuid) {
        Product product = productRepository.findById(UUID.fromString(uuid)).orElse(null);

        logger.debug("product: {}", product);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productRepository.delete(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin
    @PutMapping("/products")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getUuid()).orElse(null);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setManufacturerCode(productDTO.getManufacturerCode());
        product.setCategory(ProductCategory.valueOf(productDTO.getCategory()));

        productRepository.save(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
