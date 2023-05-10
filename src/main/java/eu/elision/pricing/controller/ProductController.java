package eu.elision.pricing.controller;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.dtoToDomain(productDto);

        productRepository.save(product);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/products/{uuid}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String uuid) {
        Product product = productRepository.findById(UUID.fromString(uuid)).orElse(null);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productMapper.domainToDto(product), HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:3000")
    @DeleteMapping("/products/{uuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String uuid) {
        Product product = productRepository.findById(UUID.fromString(uuid)).orElse(null);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productRepository.delete(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin("http://localhost:3000")
    @PutMapping("/products")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElse(null);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productRepository.save(productMapper.dtoToDomain(productDto));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
