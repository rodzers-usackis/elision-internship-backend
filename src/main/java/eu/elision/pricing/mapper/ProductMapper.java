package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;

import eu.elision.pricing.dto.ProductDTO;

/**
 * Mapper for mapping {@link Product} to {@link ProductDTO}.
 */
public interface ProductMapper {

    ProductDTO domainToDto(Product product);
    Product dtoToDomain(ProductDTO productDTO);
}

