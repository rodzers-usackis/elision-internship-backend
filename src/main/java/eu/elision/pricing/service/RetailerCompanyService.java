package eu.elision.pricing.service;

import eu.elision.pricing.dto.RetailerCompanyDto;
import java.util.List;

/**
 * Service for {@link RetailerCompanyDto}s.
 */
public interface RetailerCompanyService {
    List<RetailerCompanyDto> getRetailerCompanies();
}
