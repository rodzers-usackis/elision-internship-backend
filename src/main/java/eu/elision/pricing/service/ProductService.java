package eu.elision.pricing.service;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    void createProduct(ProductDto productDto);
    List<Product> getAllProducts();
    Product getProduct(UUID uuid);

    void deleteProductById(UUID uuid);
    Product updateProduct(ProductDto productDto);
}
