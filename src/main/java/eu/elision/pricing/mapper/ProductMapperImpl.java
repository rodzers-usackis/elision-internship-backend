package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.dto.ProductDto;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link ProductMapper}.
 */
@Component
public class ProductMapperImpl implements ProductMapper {
    @Override

    public ProductDto domainToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .ean(product.getEan())
                .manufacturerCode(product.getManufacturerCode())
                .category(String.valueOf(product.getCategory()))
                .build();
    }

    @Override
    public Product dtoToDomain(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .ean(productDto.getEan())
                .manufacturerCode(productDto.getManufacturerCode())
                .category(ProductCategory.valueOf(productDto.getCategory()))
                .build();
    }
}

