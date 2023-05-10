package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.CompanyDto;

/**
 * Mapper for mapping {@link RetailerCompany} to {@link CompanyDto}.
 */
public interface CompanyMapper {

    CompanyDto domainToDto(RetailerCompany company);

}
