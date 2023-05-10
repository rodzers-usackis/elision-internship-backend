package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.ProductDto;

/**
 * Mapper for mapping {@link Product} to {@link ProductDto}.
 */
public interface ProductMapper {

    ProductDto domainToDto(Product product);
    Product dtoToDomain(ProductDto productDTO);
}

