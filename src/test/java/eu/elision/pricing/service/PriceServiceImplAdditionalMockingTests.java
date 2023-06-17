package eu.elision.pricing.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.publishers.ScrapingFinishedEventPublisher;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class PriceServiceImplAdditionalMockingTests {

    @Mock
    private ScrapingFinishedEventPublisher scrapingFinishedEventPublisher;

    @Mock
    private PriceScrapingConfigRepository priceScrapingConfigRepository;

    @Mock
    private HttpRequestService httpRequestService;

    @Mock
    private ScraperService scraperService;

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Captor
    private ArgumentCaptor<List<Price>> priceListCaptor;

    @Captor
    private ArgumentCaptor<Price> priceCaptor;

    @Captor
    private ArgumentCaptor<PriceScrapingConfig> priceScrapingConfigCaptor;


    @Test
    void scrapeProductPricesCorrectly() throws IOException {


        Product p1 = Product.builder()
            .name("iPhone 14 Pro Max")
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c1a"))
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        Product p2 = Product.builder()
            .name("iPhone 10")
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c1b"))
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        RetailerCompany rc1 = RetailerCompany.builder()
            .name("Alternate.be")
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c1c"))
            .build();

        RetailerCompany rc2 = RetailerCompany.builder()
            .name("Coolblue.be")
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c1d"))
            .build();

        PriceScrapingConfig psc1 = PriceScrapingConfig.builder()
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c1e"))
            .commaSeparatedDecimal(true)
            .cssSelector("span.price")
            .product(p1)
            .retailerCompany(rc1)
            .active(true)
            .url(
                "invalid url here because the request should be mocked anyway p1 psc1")
            .build();

        PriceScrapingConfig psc2 = PriceScrapingConfig.builder()
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c1f"))
            .commaSeparatedDecimal(true)
            .cssSelector("div.product-details span.price")
            .product(p1)
            .retailerCompany(rc2)
            .active(true)
            .url(
                "invalid url here because the request should be mocked anyway p1 psc2")
            .build();

        PriceScrapingConfig psc3 = PriceScrapingConfig.builder()
            .id(UUID.fromString("f9153624-7234-46b7-b99c-28c255281638"))
            .commaSeparatedDecimal(true)
            .cssSelector("div.product-details span.price")
            .product(p2)
            .retailerCompany(rc2)
            .active(true)
            .url(
                "another invalid url here because the request should be mocked anyway p2 psc3")
            .build();


        Set<PriceScrapingConfig> priceScrapingConfigs = Set.of(psc1, psc2, psc3);

        Set<UUID> productIds = Set.of(p1.getId(), p2.getId());

        when(
            priceScrapingConfigRepository
                .findAllByActiveTrueAndProduct_IdIn(
                    productIds))
            .thenReturn(
                priceScrapingConfigs);
        when(httpRequestService.getHtml(psc1.getUrl())).thenReturn(
            "<span class=\"price\">1.739,00</span>");
        when(httpRequestService.getHtml(psc2.getUrl())).thenReturn(
            "<div class=\"product-details\"><span class=\"price\">2100,99 €</span></div>");
        when(httpRequestService.getHtml(psc3.getUrl())).thenReturn(
            "<div class=\"product-details\"><span class=\"price\">1000,99 €</span></div>");

        Price pr1 = Price.builder()
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c20"))
            .retailerCompany(rc1)
            .product(p1)
            .amount(1739.00)
            .build();

        Price pr2 = Price.builder()
            .id(UUID.fromString("a0e8c1a0-1b1a-4b1a-8b1a-9b1a0b1a0c21"))
            .retailerCompany(rc2)
            .product(p1)
            .amount(2100.99)
            .build();

        Price pr3 = Price.builder()
            .id(UUID.fromString("f9153624-7234-46b7-b99c-28c255281639"))
            .retailerCompany(rc2)
            .product(p2)
            .amount(1000.99)
            .build();


        when(scraperService.scrapePrice(psc1)).thenReturn(pr1);
        when(scraperService.scrapePrice(psc2)).thenReturn(pr2);
        when(scraperService.scrapePrice(psc3)).thenReturn(pr3);


        //when priceRepository.save(any) then return the argument that was originally passed
        when(priceRepository.save(any())).thenAnswer(
            (InvocationOnMock invocation) -> invocation.getArgument(0));


        priceService.scrapeProductsPrices(productIds);


        /*verify(httpRequestService).getHtml(psc2.getUrl());
        verify(httpRequestService).getHtml(psc1.getUrl());
        verify(httpRequestService).getHtml(psc3.getUrl());*/

        verify(scraperService, times(3)).scrapePrice(priceScrapingConfigCaptor.capture());

        List<PriceScrapingConfig> capturedPscs = priceScrapingConfigCaptor.getAllValues();
        assertThat(capturedPscs, containsInAnyOrder(psc1, psc2, psc3));

        verify(priceRepository, times(3)).save(priceCaptor.capture());

        List<Price> capturedPrices = priceCaptor.getAllValues();

        assertThat(capturedPrices.stream().map(Price::getAmount).collect(Collectors.toList()),
            containsInAnyOrder(pr1.getAmount(), pr2.getAmount(), pr3.getAmount()));

        verify(scrapingFinishedEventPublisher, times(1)).publish(any(LocalDateTime.class));


    }


}