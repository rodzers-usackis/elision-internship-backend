package eu.elision.pricing.controller;

import eu.elision.pricing.dto.ScrapingTaskDto;
import eu.elision.pricing.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for triggering scraping tasks.
 */
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/scraping-tasks")
public class ScrapingTaskController {

    private final PriceService priceService;


    @PostMapping
    private ResponseEntity<Void> triggerScrapingTask(@RequestBody ScrapingTaskDto scrapingTaskDto) {


        priceService.scrapeProductsPrices(scrapingTaskDto.getProductIds());

        return ResponseEntity.ok().build();
    }




}
