package eu.elision.pricing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PriceServiceImplMockingTests {

    @MockBean
    private ScraperService scraperService;

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private PriceScrapingConfigRepository priceScrapingConfigRepository;

    @Autowired
    private PriceService priceService;

    @Captor
    private ArgumentCaptor<Price> priceCaptor;


    @Test
    void scrapeAndSavePricesSuccessful() throws IOException {


        Product p1 = Product.builder()
            .name("iPhone 14 Pro Max")
            .id(UUID.randomUUID())
            .category(ProductCategory.ELECTRONICS)
            .build();

        RetailerCompany rc1 = RetailerCompany.builder()
            .name("Alternate.be")
            .id(UUID.randomUUID())
            .build();

        PriceScrapingConfig psc1 = PriceScrapingConfig.builder()
            .commaSeparatedDecimal(true)
            .cssSelector("span.price")
            .product(p1)
            .retailerCompany(rc1)
            .active(true)
            .url(
                "invalid url here because the request should be mocked anyway https://www.alternate.be/Apple/iPhone-14-Pro-Max-smartphone/html/product/1866555")
            .build();


        Price price1 = Price.builder()
            .amount(1739)
            .product(p1)
            .retailerCompany(rc1)
            .build();

        List<PriceScrapingConfig> priceScrapingConfigs = List.of(psc1);

        when(priceScrapingConfigRepository.findAll()).thenReturn(priceScrapingConfigs);
        when(scraperService.scrapePrice(psc1)).thenReturn(price1);


        priceService.scrapeAndSavePrices();


        verify(priceRepository, times(1)).save(priceCaptor.capture());

        assertEquals(price1, priceCaptor.getValue());


    }

    @Test
    void priceScrapingContinuesForOtherConfigurationsAfterAnException() throws IOException {


        Product p1 = Product.builder()
            .name("iPhone 14 Pro Max")
            .id(UUID.randomUUID())
            .category(ProductCategory.ELECTRONICS)
            .build();


        RetailerCompany rc1 = RetailerCompany.builder()
            .name("Coolblue.be")
            .id(UUID.randomUUID())
            .build();

        RetailerCompany rc2 = RetailerCompany.builder()
            .name("Alternate.be")
            .id(UUID.randomUUID())
            .build();

        PriceScrapingConfig psc1 = PriceScrapingConfig.builder()
            .commaSeparatedDecimal(true)
            .cssSelector("span.price")
            .product(p1)
            .retailerCompany(rc1)
            .active(true)
            .url(
                "invalid url here because the request should be mocked anyway https://www.coolblue.be/")
            .build();

        PriceScrapingConfig psc2 = PriceScrapingConfig.builder()
            .commaSeparatedDecimal(true)
            .cssSelector("span.price")
            .product(p1)
            .retailerCompany(rc1)
            .active(true)
            .url(
                "invalid url here because the request should be mocked anyway https://www.alternate.be/Apple/iPhone-14-Pro-Max-smartphone/html/product/1866555")
            .build();


        Price price2 = Price.builder()
            .amount(1739)
            .product(p1)
            .retailerCompany(rc2)
            .build();

        List<PriceScrapingConfig> priceScrapingConfigs = List.of(psc1, psc2);

        when(priceScrapingConfigRepository.findAll()).thenReturn(priceScrapingConfigs);
        when(scraperService.scrapePrice(psc1)).thenThrow(new NumberFormatException());
        when(scraperService.scrapePrice(psc2)).thenReturn(price2);


        priceService.scrapeAndSavePrices();


        verify(priceRepository, times(1)).save(priceCaptor.capture());
        assertEquals(price2, priceCaptor.getValue());


    }


}