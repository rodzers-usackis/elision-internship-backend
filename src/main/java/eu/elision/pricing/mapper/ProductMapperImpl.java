package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;

import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.dto.ProductDTO;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link ProductMapper}.
 */
@Component
public class ProductMapperImpl implements ProductMapper {
    @Override

    public ProductDTO domainToDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .ean(product.getEan())
                .manufacturerCode(product.getManufacturerCode())
                .category(String.valueOf(product.getCategory()))
                .build();
    }

    @Override
    public Product dtoToDomain(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .ean(productDTO.getEan())
                .manufacturerCode(productDTO.getManufacturerCode())
                .category(ProductCategory.valueOf(productDTO.getCategory()))
                .build();
    }
}

