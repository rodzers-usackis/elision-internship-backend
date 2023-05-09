package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.dto.CompanyDto;
import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.dto.TimestampAmountDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PriceHistoryMapperImpl implements PriceHistoryMapper{

    private final CompanyMapper companyMapper;
    private final ProductMapper productMapper;

    @Override
    public PriceHistoryDto domainToDto(List<Price> prices) {

        List<TimestampAmountDto> data = prices.stream()
            .map(price -> TimestampAmountDto.builder()
                .timestamp(price.getTimestamp())
                .amount(price.getAmount())
                .build())
            .collect(java.util.stream.Collectors.toList());

        CompanyDto companyDto = companyMapper.domainToDto(prices.get(0).getRetailerCompany());
        ProductDto productDto = productMapper.domainToDto(prices.get(0).getProduct());

        return PriceHistoryDto.builder()
            .company(companyDto)
            .product(productDto)
            .data(data)
            .build();
    }
}
