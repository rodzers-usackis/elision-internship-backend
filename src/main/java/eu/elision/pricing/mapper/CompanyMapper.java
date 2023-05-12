package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.RetailerCompanyDto;

/**
 * Mapper for mapping {@link RetailerCompany} to {@link RetailerCompanyDto}.
 */
public interface CompanyMapper {

    RetailerCompanyDto domainToDto(RetailerCompany company);

}
