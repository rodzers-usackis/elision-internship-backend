package eu.elision.pricing.controller;

import eu.elision.pricing.dto.PriceHistoryDto;
import eu.elision.pricing.service.PriceService;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for price and price history data.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/prices")
public class PriceRestController {

    private final PriceService priceService;

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
