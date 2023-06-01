package eu.elision.pricing.service;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RetailerCompanyServiceImpl implements RetailerCompanyService {

    private final RetailerCompanyRepository retailerCompanyRepository;

    @Override
    public List<RetailerCompany> getRetailerCompanies() {
        return retailerCompanyRepository.findAll();
    }
}
