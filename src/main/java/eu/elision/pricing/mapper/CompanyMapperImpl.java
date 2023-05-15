package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.RetailerCompanyDto;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link CompanyMapper}.
 */
@Component
public class CompanyMapperImpl implements CompanyMapper {
    @Override
    public RetailerCompanyDto domainToDto(RetailerCompany company) {
        return RetailerCompanyDto.builder()
            .id(company.getId())
            .name(company.getName())
            .build();
    }
}
