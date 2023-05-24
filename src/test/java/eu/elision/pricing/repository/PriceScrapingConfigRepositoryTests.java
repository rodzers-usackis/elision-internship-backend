package eu.elision.pricing.repository;

import static org.junit.jupiter.api.Assertions.*;

import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class PriceScrapingConfigRepositoryTests {


    @Autowired
    private PriceScrapingConfigRepository priceScrapingConfigRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RetailerCompanyRepository retailerCompanyRepository;

    @Test
    public void testFindAllByActiveTrueAndProduct_IdIn() {
        // Create test data
        Product product1 = Product.builder()
            .name("Product 1")
            .description("Description 1")
            .ean("EAN1")
            .manufacturerCode("Manufacturer1")
            .category(ProductCategory.ELECTRONICS)
            .build();
        product1 = productRepository.save(product1);

        Product product2 = Product.builder()
            .name("Product 2")
            .description("Description 2")
            .ean("EAN2")
            .manufacturerCode("Manufacturer2")
            .category(ProductCategory.ELECTRONICS)
            .build();
        product2 = productRepository.save(product2);

        Product product3 = Product.builder()
            .name("Product 3")
            .description("Description 3")
            .ean("EAN3")
            .manufacturerCode("Manufacturer3")
            .category(ProductCategory.ELECTRONICS)
            .build();
        product3 = productRepository.save(product3);

        RetailerCompany retailerCompany = retailerCompanyRepository.save(RetailerCompany.builder()
            .name("Retailer Company")
            .website("http://example.com")
            .categoriesProductsSold(Set.of(ProductCategory.ELECTRONICS))
            .build());

        PriceScrapingConfig config1 = PriceScrapingConfig.builder()
            .cssSelector(".selector1")
            .url("http://example.com")
            .active(true)
            .product(product1)
            .retailerCompany(retailerCompany)
            .commaSeparatedDecimal(false)
            .build();

        PriceScrapingConfig config2 = PriceScrapingConfig.builder()
            .cssSelector(".selector2")
            .url("http://example.com")
            .active(true)
            .product(product2)
            .retailerCompany(retailerCompany)
            .commaSeparatedDecimal(false)
            .build();

        PriceScrapingConfig config3 = PriceScrapingConfig.builder()
            .cssSelector(".selector3")
            .url("http://example.com")
            .active(false)
            .product(product1)
            .retailerCompany(retailerCompany)
            .commaSeparatedDecimal(false)
            .build();

        PriceScrapingConfig config4 = PriceScrapingConfig.builder()
            .cssSelector(".selector4")
            .url("http://example.com")
            .active(true)
            .product(product3)
            .retailerCompany(retailerCompany)
            .commaSeparatedDecimal(false)
            .build();

        priceScrapingConfigRepository.saveAll(Arrays.asList(config1, config2, config3, config4));

        // Test the repository method
        List<UUID> productIds = Arrays.asList(product1.getId(), product2.getId());
        List<PriceScrapingConfig> result = priceScrapingConfigRepository.findAllByActiveTrueAndProduct_IdIn(productIds);

        // Assert the result
        assertEquals(4, priceScrapingConfigRepository.count());
        assertEquals(2, result.size());
        assertTrue(result.contains(config1));
        assertTrue(result.contains(config2));

    }

}