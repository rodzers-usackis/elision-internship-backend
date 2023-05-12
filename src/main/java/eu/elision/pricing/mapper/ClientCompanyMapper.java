package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.dto.ClientCompanyDto;

public interface ClientCompanyMapper {

    ClientCompanyDto domainToDto(ClientCompany clientCompany);
    ClientCompany dtoToDomain(ClientCompanyDto clientCompanyDTO);
}
