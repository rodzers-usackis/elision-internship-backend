package eu.elision.pricing.controller;

import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.service.PriceService;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for price and price history data.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/prices")
public class PriceRestController {

    private final PriceService priceService;

    @CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
    @GetMapping("/products/{id}")
    public ResponseEntity<PriceHistoryDto> getPriceHistoryForProduct(@PathVariable("id")
                                                                         UUID productId,
                                                                     @RequestParam("before")
                                                                         LocalDate before,
                                                                     @RequestParam("after")
                                                                         LocalDate after) {

        return ResponseEntity.ok(priceService.getPriceHistory(productId, before, after));
    }


}
