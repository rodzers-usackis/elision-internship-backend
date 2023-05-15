package eu.elision.pricing.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import eu.elision.pricing.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PriceRestControllerIntegrationTests {

    @Autowired
    private RetailerCompanyRepository retailerCompanyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private UUID product1Id;
    private UUID product2Id;
    private User user;


    @BeforeAll
    void setUp() {

        RetailerCompany company1 = RetailerCompany.builder()
            .name("Company 1")
            .website("www.company1.com")
            .categoriesProductsSold(Set.of(ProductCategory.ELECTRONICS))
            .build();

        RetailerCompany company2 = RetailerCompany.builder()
            .name("Company 2")
            .website("www.company2.com")
            .build();

        Product product = Product.builder()
            .name("Product 1")
            .category(ProductCategory.ELECTRONICS)
            .description("A product")
            .ean("1234567890123")
            .manufacturerCode("1234567890")
            .build();


        Product product2 = Product.builder()
            .name("Product 2")
            .category(ProductCategory.ELECTRONICS)
            .description("A product2")
            .ean("121212121")
            .manufacturerCode("999999999")
            .build();

        productRepository.saveAll(List.of(product, product2));
        retailerCompanyRepository.saveAll(List.of(company1, company2));

        product1Id = product.getId();
        product2Id = product2.getId();

        for (int i = 0; i < 30; i++) {
            Price price = Price.builder()
                .product(product)
                .retailerCompany(company1)
                .amount((1 + Math.random()) * 500)
                .timestamp(LocalDateTime.now().minusDays(i))
                .build();

            priceRepository.save(price);
        }

        for (int i = 0; i < 10; i++) {
            Price price = Price.builder()
                .product(product)
                .retailerCompany(company2)
                .amount((1 + Math.random()) * 7500)
                .timestamp(LocalDateTime.now().minusDays(i))
                .build();

            priceRepository.save(price);
        }

        for (int i = 0; i < 5; i++) {
            Price price = Price.builder()
                .product(product2)
                .retailerCompany(company2)
                .amount((1 + Math.random()) * 1000)
                .timestamp(LocalDateTime.now().minusDays(i))
                .build();

            priceRepository.save(price);
        }


        User user = User.builder()
            .email("test@test.com")
            .password("somepassword")
            .role(Role.CLIENT)
            .firstName("Test")
            .lastName("Test")
            .build();

        userRepository.save(user);
        this.user = user;
    }


    @Test
    void priceHistoryReturnedCorrectly() throws Exception {

        log.debug(">>> Product 1 id: {}", product1Id);
        log.debug(">>> Product 2 id: {}", product2Id);

        mockMvc.perform(get("/api/prices/products/" + product1Id)
                .param("after", LocalDate.now().minusDays(100).toString())
                .param("before", LocalDate.now().toString())
                .with(user(user))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.[0].timestampAmounts").isArray())
            .andExpect(jsonPath("$.data.length()").value(2));


        mockMvc.perform(get("/api/prices/products/" + product2Id)
                .param("after", LocalDate.now().minusDays(100).toString())
                .param("before", LocalDate.now().toString())
                .with(user(user))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.[0].timestampAmounts").isArray())
            .andExpect(jsonPath("$.data.length()").value(1));


    }


    @Test
    void unauthenticatedUserCannotAccessPrices() throws Exception {
        mockMvc.perform(get("/api/prices/products/" + product1Id)
                .param("after", LocalDate.now().minusDays(100).toString())
                .param("before", LocalDate.now().toString())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @AfterAll
    void cleanUp() {
        priceRepository.deleteAll();
        productRepository.deleteAll();
        retailerCompanyRepository.deleteAll();
        userRepository.deleteAll();
    }


}