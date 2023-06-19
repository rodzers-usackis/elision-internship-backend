package eu.elision.pricing.service;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.RetailerCompanyDto;
import eu.elision.pricing.mapper.RetailerCompanyMapper;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link RetailerCompanyService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RetailerCompanyServiceImpl implements RetailerCompanyService {

    private final RetailerCompanyRepository retailerCompanyRepository;
    private final RetailerCompanyMapper retailerCompanyMapper;

    @Override
    public List<RetailerCompanyDto> getRetailerCompanies() {
        List<RetailerCompany> companies = retailerCompanyRepository.findAll();
        return companies.stream()
            .map(retailerCompanyMapper::domainToDto)
            .collect(Collectors.toList());
    }
}
