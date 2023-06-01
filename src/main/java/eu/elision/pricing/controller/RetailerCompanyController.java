package eu.elision.pricing.controller;

import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.service.RetailerCompanyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for {@link RetailerCompany}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RetailerCompanyController {

    private final RetailerCompanyService retailerCompanyService;

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/retailer-companies")
    ResponseEntity<List<RetailerCompany>> getRetailerCompanies(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(retailerCompanyService.getRetailerCompanies(), HttpStatus.OK);
    }
}
