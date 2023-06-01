package eu.elision.pricing.service;

import eu.elision.pricing.domain.RetailerCompany;
import java.util.List;

/**
 * Service for {@link RetailerCompany}s.
 */
public interface RetailerCompanyService {
    List<RetailerCompany> getRetailerCompanies();
}
