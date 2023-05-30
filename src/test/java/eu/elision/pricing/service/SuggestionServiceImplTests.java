package eu.elision.pricing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.repository.SuggestedPriceRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SuggestionServiceImplTests {

    @InjectMocks
    private SuggestionServiceImpl suggestionServiceImpl;

    @Mock
    private SuggestedPriceRepository suggestedPriceRepository;

    @Test
    void suggestedPriceNeverLowerThanMinPrice() {

        Product product = Product.builder()
            .id(UUID.randomUUID())
            .name("Test Product")
            .ean("1234567890123")
            .manufacturerCode("1234567890")
            .description("Test Product Description")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();


        RetailerCompany retailerCompany = RetailerCompany.builder()
            .id(UUID.randomUUID())
            .name("Test Retailer")
            .website("https://www.testretailer.com")
            .build();

        RetailerCompany retailerCompany2 = RetailerCompany.builder()
            .id(UUID.randomUUID())
            .name("Test Retailer 2")
            .website("https://www.testretailer2.com")
            .build();

        TrackedProduct trackedProduct1 = TrackedProduct.builder()
            .id(UUID.randomUUID())
            .product(product)
            .productPurchaseCost(100.0)
            .productSellPrice(200.0)
            .minPrice(150.0)
            .build();


        Price price1 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany)
            .product(product)
            .amount(100.0)
            .build();

        Price price2 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany2)
            .product(product)
            .amount(90.0)
            .build();


        List<Price> prices1 = List.of(price1, price2);

        Price price3 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany)
            .product(product)
            .amount(0.0)
            .build();

        Price price4 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany2)
            .product(product)
            .amount(999)
            .build();


        when(suggestedPriceRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Price> prices2 = List.of(price3, price4);

        assertTrue(suggestionServiceImpl.calculateSuggestedPrice(trackedProduct1, prices1)
            .getSuggestedPrice() >= trackedProduct1.getMinPrice());
        verify(suggestedPriceRepository, times(1)).save(any());
        assertTrue(suggestionServiceImpl.calculateSuggestedPrice(trackedProduct1, prices2)
            .getSuggestedPrice() >= trackedProduct1.getMinPrice());
        verify(suggestedPriceRepository, times(2)).save(any());

    }

    @Test
    void suggestedPriceNeverLowerThanCostIfNoMinPrice() {
        Product product = Product.builder()
            .id(UUID.randomUUID())
            .name("Test Product")
            .ean("1234567890123")
            .manufacturerCode("1234567890")
            .description("Test Product Description")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();


        RetailerCompany retailerCompany = RetailerCompany.builder()
            .id(UUID.randomUUID())
            .name("Test Retailer")
            .website("https://www.testretailer.com")
            .build();

        RetailerCompany retailerCompany2 = RetailerCompany.builder()
            .id(UUID.randomUUID())
            .name("Test Retailer 2")
            .website("https://www.testretailer2.com")
            .build();

        TrackedProduct trackedProduct1 = TrackedProduct.builder()
            .id(UUID.randomUUID())
            .product(product)
            .productPurchaseCost(100.0)
            .productSellPrice(200.0)
            .minPrice(150.0)
            .build();


        Price price1 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany)
            .product(product)
            .amount(100.0)
            .build();

        Price price2 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany2)
            .product(product)
            .amount(90.0)
            .build();


        List<Price> prices1 = List.of(price1, price2);

        Price price3 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany)
            .product(product)
            .amount(0.0)
            .build();

        Price price4 = Price.builder()
            .id(UUID.randomUUID())
            .retailerCompany(retailerCompany2)
            .product(product)
            .amount(999)
            .build();

        when(suggestedPriceRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Price> prices2 = List.of(price3, price4);

        assertTrue(suggestionServiceImpl.calculateSuggestedPrice(trackedProduct1, prices1)
            .getSuggestedPrice() >= trackedProduct1.getProductPurchaseCost());
        verify(suggestedPriceRepository, times(1)).save(any());
        assertTrue(suggestionServiceImpl.calculateSuggestedPrice(trackedProduct1, prices2)
            .getSuggestedPrice() >= trackedProduct1.getProductPurchaseCost());
        verify(suggestedPriceRepository, times(2)).save(any());

    }

    @Test
    void returnNullWhenNoPrices() {
        Product product = Product.builder()
            .id(UUID.randomUUID())
            .name("Test Product")
            .ean("1234567890123")
            .manufacturerCode("1234567890")
            .description("Test Product Description")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();


        RetailerCompany retailerCompany = RetailerCompany.builder()
            .id(UUID.randomUUID())
            .name("Test Retailer")
            .website("https://www.testretailer.com")
            .build();

        RetailerCompany retailerCompany2 = RetailerCompany.builder()
            .id(UUID.randomUUID())
            .name("Test Retailer 2")
            .website("https://www.testretailer2.com")
            .build();

        TrackedProduct trackedProduct1 = TrackedProduct.builder()
            .id(UUID.randomUUID())
            .product(product)
            .productPurchaseCost(100.0)
            .productSellPrice(200.0)
            .minPrice(150.0)
            .build();


        List<Price> prices1 = List.of();


        when(suggestedPriceRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        assertNull(suggestionServiceImpl.calculateSuggestedPrice(trackedProduct1, prices1));
        verify(suggestedPriceRepository, times(0)).save(any());


    }


}