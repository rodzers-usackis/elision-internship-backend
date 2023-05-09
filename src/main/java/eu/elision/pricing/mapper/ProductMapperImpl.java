package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper{
    @Override
    public ProductDto domainToDto(Product product) {
        return ProductDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .ean(product.getEan())
            .manufacturerCode(product.getManufacturerCode())
            .category(product.getCategory())
            .build();
    }
}
