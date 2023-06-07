package eu.elision.pricing.controller;

import eu.elision.pricing.dto.ScrapingTaskDto;
import eu.elision.pricing.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for triggering scraping tasks.
 */
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://next-js-frontend-dot-pricing-as-a-service.ew.r.appspot.com", "http://81.240.96.43:3000", "http://81.240.96.43", "http://localhost:3000"})
@RestController
@RequestMapping("/api/scraping-tasks")
public class ScrapingTaskController {

    private final PriceService priceService;


    @PostMapping
    private ResponseEntity<Void> triggerScrapingTask(@RequestBody ScrapingTaskDto scrapingTaskDto) {

        log.info(">>> Triggering scraping task for products: {}", scrapingTaskDto.getProductIds());

        priceService.scrapeProductsPrices(scrapingTaskDto.getProductIds());

        return ResponseEntity.ok().build();
    }




}
