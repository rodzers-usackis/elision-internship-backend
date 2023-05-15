package eu.elision.pricing.service;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.exceptions.NotFoundException;
import eu.elision.pricing.mapper.ProductMapper;
import eu.elision.pricing.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = productMapper.dtoToDomain(productDto);

        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public Product getProduct(UUID uuid) {

        Product product = productRepository.findById(uuid).orElse(null);

        if (product == null) {
            throw new NotFoundException(String.format("Invalid product id: %s", uuid));
        }

        return productRepository.findById(uuid).orElse(null);
    }

    @Override
    public void deleteProductById(UUID uuid) {

        Product product = productRepository.findById(uuid).orElse(null);

        if (product == null) {
            throw new NotFoundException(String.format("Invalid product id: %s", uuid));
        }

        productRepository.deleteById(uuid);
    }

    @Override
    public Product updateProduct(ProductDto productDto) {

        Product product = productRepository.findById(productDto.getId()).orElse(null);

        if (product == null) {
            throw new NotFoundException(String.format("Invalid product id: %s", productDto.getId()));
        }

        product = productMapper.dtoToDomain(productDto);
        productRepository.save(product);

        return product;
    }

}
