package eu.elision.pricing.mapper;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.dto.ClientCompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientCompanyMapperImpl implements  ClientCompanyMapper {

    @Override
    public ClientCompanyDto domainToDto(ClientCompany clientCompany) {
        return null;
    }

    @Override
    public ClientCompany dtoToDomain(ClientCompanyDto clientCompanyDTO) {
        return null;
    }
}
