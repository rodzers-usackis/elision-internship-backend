package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.dto.ClientCompanyDto;

/**
 * Mapper interface for mapping
 * {@link ClientCompany} and {@link ClientCompanyDto}.
 */
public interface ClientCompanyMapper {

    ClientCompanyDto domainToDto(ClientCompany clientCompany);

    ClientCompany dtoToDomain(ClientCompanyDto clientCompanyDto);
}
