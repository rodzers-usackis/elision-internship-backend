package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.ProductDto;

public interface ProductMapper {

    ProductDto domainToDto(Product product);

}
