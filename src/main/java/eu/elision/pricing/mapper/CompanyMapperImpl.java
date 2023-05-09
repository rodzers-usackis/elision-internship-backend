package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.CompanyDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperImpl implements CompanyMapper{
    @Override
    public CompanyDto domainToDto(RetailerCompany company) {
        return CompanyDto.builder()
            .id(company.getId())
            .name(company.getName())
            .build();
    }
}
