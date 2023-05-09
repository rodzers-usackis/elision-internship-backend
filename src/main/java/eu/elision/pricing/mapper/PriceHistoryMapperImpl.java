package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.CompanyDto;
import eu.elision.pricing.dto.CompanyTimestampAmountsDto;
import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.dto.TimestampAmountDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link PriceHistoryMapper}.
 */
@RequiredArgsConstructor
@Component
public class PriceHistoryMapperImpl implements PriceHistoryMapper {

    private final CompanyMapper companyMapper;
    private final ProductMapper productMapper;

    @Override
    public PriceHistoryDto domainToDto(List<Price> prices) {


        ProductDto productDto = productMapper.domainToDto(prices.get(0).getProduct());

        List<CompanyTimestampAmountsDto> companyTimestampAmounts = new ArrayList<>();

        List<RetailerCompany> companies =
            prices.stream()
                .map(Price::getRetailerCompany)
                .distinct()
                .collect(Collectors.toList());

        companies.forEach(company -> {

            List<TimestampAmountDto> timestampAmounts = prices.stream()
                .filter(price -> price.getRetailerCompany().equals(company))
                .map(price -> TimestampAmountDto.builder()
                    .timestamp(price.getTimestamp())
                    .amount(price.getAmount())
                    .build())
                .collect(Collectors.toList());

            CompanyDto companyDto = companyMapper.domainToDto(company);

            CompanyTimestampAmountsDto companyTimestampAmountsDto =
                CompanyTimestampAmountsDto.builder()
                    .company(companyDto)
                    .timestampAmounts(timestampAmounts)
                    .build();

            companyTimestampAmounts.add(companyTimestampAmountsDto);
        });


        return PriceHistoryDto.builder()
            .product(productDto)
            .data(companyTimestampAmounts)
            .build();
    }
}
