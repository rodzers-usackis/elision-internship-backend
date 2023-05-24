package eu.elision.pricing.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.dto.PriceHistoryDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
class PriceHistoryMapperImplTests {

    @Autowired
    private PriceHistoryMapper priceHistoryMapperImpl;

    @Test
    void pricesFromMultipleCompaniesMappedCorrectly() {

        RetailerCompany company1 = RetailerCompany.builder()
            .id(java.util.UUID.randomUUID())
            .name("Company 1")
            .website("www.company1.com")
            .build();

        RetailerCompany company2 = RetailerCompany.builder()
            .id(java.util.UUID.randomUUID())
            .name("Company 2")
            .website("www.company2.com")
            .build();

        Product product = Product.builder()
            .id(java.util.UUID.randomUUID())
            .name("Product 1")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .description("A product")
            .ean("1234567890123")
            .manufacturerCode("1234567890")
            .build();

        Price price1 = Price.builder()
            .id(java.util.UUID.randomUUID())
            .product(product)
            .retailerCompany(company1)
            .timestamp(java.time.LocalDateTime.now())
            .amount(100.0)
            .build();

        Price price2 = Price.builder()
            .id(java.util.UUID.randomUUID())
            .product(product)
            .amount(200.0)
            .timestamp(LocalDateTime.of(2022, 5, 1, 0, 10))
            .retailerCompany(company1)
            .build();

        Price price3 = Price.builder()
            .id(java.util.UUID.randomUUID())
            .product(product)
            .amount(300.0)
            .timestamp(LocalDateTime.of(2023, 1, 19, 8, 0))
            .retailerCompany(company2)
            .build();

        Price price4 = Price.builder()
            .id(java.util.UUID.randomUUID())
            .product(product)
            .amount(400.0)
            .timestamp(LocalDateTime.of(2021, 1, 5, 1, 25))
            .retailerCompany(company1)
            .build();


        List<Price> prices = List.of(price1, price2, price3, price4);

        PriceHistoryDto priceHistoryDto = priceHistoryMapperImpl.domainToDto(prices);

        //2 unique companies = 2 lists
        assertEquals(2, priceHistoryDto.getData().size());
        //3 prices for company1
        assertEquals(3, priceHistoryDto.getData().get(0).getTimestampAmounts().size());
        assertEquals(company1.getId(), priceHistoryDto
            .getData()
            .get(0)
            .getRetailerCompanyDto()
            .getId());
        //1 price for company2
        assertEquals(1, priceHistoryDto.getData().get(1).getTimestampAmounts().size());
        assertEquals(company2.getId(),
            priceHistoryDto.getData().get(1).getRetailerCompanyDto().getId());


    }

}