package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.RetailerCompanyDto;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link RetailerCompanyMapper}.
 */
@Component
public class RetailerRetailerCompanyMapperImpl implements RetailerCompanyMapper {
    @Override
    public RetailerCompanyDto domainToDto(RetailerCompany company) {
        return RetailerCompanyDto.builder()
            .id(company.getId())
            .name(company.getName())
            .build();
    }

    @Override
    public RetailerCompany dtoToDomain(RetailerCompanyDto companyDto) {
        return RetailerCompany.builder()
            .id(companyDto.getId())
            .name(companyDto.getName())
            .build();
    }
}
