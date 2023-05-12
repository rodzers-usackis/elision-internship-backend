package eu.elision.pricing.controller;

import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.ClientCompanyDto;
import eu.elision.pricing.dto.TrackedProductDto;
import eu.elision.pricing.mapper.ClientCompanyMapper;
import eu.elision.pricing.mapper.TrackedProductMapper;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientCompanyController {

    private final ClientCompanyRepository clientCompanyRepository;
    private final ClientCompanyMapper clientCompanyMapper;
    private final UserRepository userRepository;
    private final TrackedProductMapper trackedProductMapper;
    private final Logger logger = LoggerFactory.getLogger(ClientCompanyController.class);

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/client-company/tracked-products")
    public ResponseEntity<ClientCompanyDto> createTrackedProduct(
            @AuthenticationPrincipal User user, @RequestBody TrackedProductDto trackedProductDto) {

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ClientCompany clientCompany = clientCompanyRepository.findById(user.getId()).orElse(null);

        assert clientCompany != null;
        if (!clientCompany.getUsers().contains(user)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        TrackedProduct trackedProduct = trackedProductMapper.dtoToDomain(trackedProductDto);
        clientCompanyRepository.saveTrackedProduct(clientCompany.getId() ,trackedProduct);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/client-company/tracked-products")
    public ResponseEntity<List<TrackedProduct>> getTrackedProducts(@AuthenticationPrincipal User user) {

        logger.debug("getTrackedProducts------------------------" +  user.getClientCompany().getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ClientCompany clientCompany = clientCompanyRepository.findById(user.getClientCompany().getId()).orElse(null);

        if (clientCompany == null || !clientCompany.getUsers().contains(user)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TrackedProduct> trackedProducts = clientCompany.getTrackedProducts();

        return new ResponseEntity<>(trackedProducts, HttpStatus.OK);
    }

//    @CrossOrigin("http://localhost:3000")
//    @GetMapping("/client-company/tracked-products/{uuid}")
//    public ResponseEntity<TrackedProduct> getTrackedProduct(@AuthenticationPrincipal User user, @PathVariable String uuid) {
//
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        ClientCompany clientCompany = clientCompanyRepository.findById(user.getClientCompany().getId()).orElse(null);
//
//        if (clientCompany == null || !clientCompany.getUsers().contains(user)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        TrackedProduct trackedProduct = clientCompanyRepository.findTrackedProductById(clientCompany.getId(), UUID.fromString(uuid));
//
//
//        return new ResponseEntity<>(trackedProduct, HttpStatus.OK);
//    }
}
