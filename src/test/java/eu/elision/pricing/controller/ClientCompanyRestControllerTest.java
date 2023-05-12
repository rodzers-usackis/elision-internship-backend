package eu.elision.pricing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.elision.pricing.domain.*;
import eu.elision.pricing.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientCompanyRestControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PriceScrapingConfigRepository priceScrapingConfigRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RetailerCompanyRepository retailerCompanyRepository;
    @Autowired
    private TrackedProductRepository trackedProductRepository;

    private TrackedProduct trackedProduct1;
    private TrackedProduct trackedProduct2;
    private TrackedProduct trackedProduct3;
    private User user;

    @BeforeEach
    void setup() {
        // Addresses
        Address address = Address.builder()
                .street("Main Street")
                .streetNumber("123")
                .apartmentNumber("1")
                .city("New York")
                .postalCode("10001")
                .country("USA")
                .build();


        // Products
        Product product1 = Product.builder()
                .name("Apple iPhone 12 Pro")
                .description("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")
                .ean("0190199731320")
                .manufacturerCode("MGMH3LL/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product1);

        Product product2 = Product.builder()
                .name("Apple iPhone 14 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401179")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product2);

        Product product3 = Product.builder()
                .name("Apple iPhone 14")
                .description("128GB, Middernacht")
                .ean("194253408253")
                .manufacturerCode("MPUF3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product3);


        // Client Company
        ClientCompany clientCompany = ClientCompany.builder()
                .address(address)
                .name("Elision")
                .website("https://elision.eu")
                .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.ELECTRONICS)))
                .build();

        clientCompanyRepository.save(clientCompany);

        User user = User.builder()
                .email("test@test3.com")
                .password("somepassword")
                .role(Role.CLIENT)
                .firstName("Test")
                .lastName("Test")
                .clientCompany(clientCompany)
                .build();

        clientCompany.setUsers((List.of(user)));

        userRepository.save(user);
        this.user = user;

        // Tracked Products
        this.trackedProduct1 = TrackedProduct.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(product1)
                .clientCompany(clientCompany)
                .build();

        trackedProductRepository.save(trackedProduct1);

        trackedProduct2 = TrackedProduct.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(product2)
                .clientCompany(clientCompany)
                .build();

        trackedProductRepository.save(trackedProduct2);

        trackedProduct3 = TrackedProduct.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(product3)
                .clientCompany(clientCompany)
                .build();

        trackedProductRepository.save(trackedProduct3);

        clientCompany.setTrackedProducts(new ArrayList<>(List.of(
                trackedProduct1,
                trackedProduct2,
                trackedProduct3
        )));

        clientCompanyRepository.save(clientCompany);

        user.setClientCompany(clientCompany);
        userRepository.save(user);


        // Retailer Company
        RetailerCompany retailerCompany = RetailerCompany.builder()
                .name("Amazon")
                .website("https://amazon.com")
                .build();

        retailerCompanyRepository.save(retailerCompany);


        // Price Scraping Config
        PriceScrapingConfig priceScrapingConfig = PriceScrapingConfig.builder()
                .cssSelector("//span[@id='priceblock_ourprice']")
                .url("https://www.amazon.com/Apple-iPhone-128GB-Graphite-Unlocked/dp/B08L5TNJHG/ref=sr_1_3?dchild=1&keywords=iphone+12+pro&qid=1621430008&sr=8-3")
                .active(true)
                .retailerCompany(retailerCompany)
                .product(product1)
                .commaSeparatedDecimal(true)
                .build();

        priceScrapingConfigRepository.save(priceScrapingConfig);
    }

    @Test
    void givenTrackedProductsWhenGetTrackedProductsThenStatus200() throws Exception {
        mockMvc.perform(get("/api/client-company/tracked-products")
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

//    @Test
//    void givenTrackedProductsWhenGetTrackedProductByIdThenStatus200() throws Exception {
//        mockMvc.perform(get("/api/client-company/tracked-products/{trackedProductId}", trackedProduct1.getId().toString())
//                        .with(user(user))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productPurchaseCost", is(trackedProduct1.getProductPurchaseCost())))
//                .andExpect(jsonPath("$.productSellPrice", is(trackedProduct1.getProductSellPrice())));
//    }
}
