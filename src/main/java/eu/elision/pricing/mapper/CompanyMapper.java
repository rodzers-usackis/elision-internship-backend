package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.CompanyDto;

public interface CompanyMapper {

    CompanyDto domainToDto(RetailerCompany company);

}
