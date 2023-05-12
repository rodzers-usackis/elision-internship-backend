package eu.elision.pricing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.elision.pricing.domain.*;
import eu.elision.pricing.dto.TrackedProductDto;
import eu.elision.pricing.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrackedProductRestControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @Autowired
    private ClientCompanyRepository clientCompanyRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TrackedProductRepository trackedProductRepository;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;
    private Product product6;
    private TrackedProduct trackedProduct1;
    private TrackedProduct trackedProduct2;
    private TrackedProduct trackedProduct3;
    private TrackedProduct trackedProduct4;
    private TrackedProduct trackedProduct5;
    private User user;
    private User user2;


    @BeforeAll
    void setup() {
        // First user
        Address address = Address.builder()
                .street("Main Street")
                .streetNumber("123")
                .apartmentNumber("1")
                .city("New York")
                .postalCode("10001")
                .country("USA")
                .build();


        this.product1 = Product.builder()
                .name("Apple iPhone 12 Pro")
                .description("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")
                .ean("0190199731320")
                .manufacturerCode("MGMH3LL/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product1);

        this.product2 = Product.builder()
                .name("Apple iPhone 14 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401179")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product2);

        this.product3 = Product.builder()
                .name("Apple iPhone 14")
                .description("128GB, Middernacht")
                .ean("194253408253")
                .manufacturerCode("MPUF3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product3);

        this.product6 = Product.builder()
                .name("Apple iPhone 14")
                .description("128GB, Middernacht")
                .ean("194253408254")
                .manufacturerCode("MPUF3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product6);

        ClientCompany clientCompany = ClientCompany.builder()
                .address(address)
                .name("Elision")
                .website("https://elision.eu")
                .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.ELECTRONICS)))
                .build();

        clientCompanyRepository.save(clientCompany);

        this.user = User.builder()
                .email("test@trackedtest1.com")
                .password("somepassword")
                .role(Role.CLIENT)
                .firstName("Test")
                .lastName("Test")
                .clientCompany(clientCompany)
                .build();

        clientCompany.setUsers((List.of(user)));

        userRepository.save(user);


        // Tracked Products
        this.trackedProduct1 = TrackedProduct.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(product1)
                .clientCompany(clientCompany)
                .build();

        trackedProductRepository.save(trackedProduct1);

        this.trackedProduct2 = TrackedProduct.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(product2)
                .clientCompany(clientCompany)
                .build();

        trackedProductRepository.save(trackedProduct2);

        this.trackedProduct3 = TrackedProduct.builder()
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


        // Second user
        Address address2 = Address.builder()
                .street("Main Street")
                .streetNumber("123")
                .apartmentNumber("1")
                .city("New York")
                .postalCode("10001")
                .country("USA")
                .build();

        this.product4 = Product.builder()
                .name("Apple iPhone 12 Pro")
                .description("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")
                .ean("0190199731320")
                .manufacturerCode("MGMH3LL/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product4);

        this.product5 = Product.builder()
                .name("Apple iPhone 14 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401179")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product5);

        ClientCompany clientCompany2 = ClientCompany.builder()
                .address(address2)
                .name("Elision")
                .website("https://elision.eu")
                .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.ELECTRONICS)))
                .build();

        clientCompanyRepository.save(clientCompany2);

        this.user2 = User.builder()
                .email("test@trackedtest2.com")
                .password("somepassword")
                .role(Role.CLIENT)
                .firstName("Test")
                .lastName("Test")
                .clientCompany(clientCompany2)
                .build();

        clientCompany2.setUsers((List.of(user2)));
        userRepository.save(user2);

        this.trackedProduct4 = TrackedProduct.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(product4)
                .clientCompany(clientCompany2)
                .build();

        trackedProductRepository.save(trackedProduct4);

        this.trackedProduct5 = TrackedProduct.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(product5)
                .clientCompany(clientCompany2)
                .build();

        trackedProductRepository.save(trackedProduct5);

        clientCompany2.setTrackedProducts(new ArrayList<>(List.of(
                trackedProduct4,
                trackedProduct5
        )));

        clientCompanyRepository.save(clientCompany2);

        user2.setClientCompany(clientCompany2);
        userRepository.save(user2);
    }

    @Test
    void givenTrackedProductsWhenPostTrackedProductThenStatus201() throws Exception {
        TrackedProductDto trackedProductDto = TrackedProductDto.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(String.valueOf(product4.getId()))
                .clientCompany(String.valueOf(user2.getClientCompany().getId()))
                .build();

        mockMvc.perform(post("/api/client-company/tracked-products")
                        .with(user(user2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackedProductDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.product.id", is(trackedProductDto.getProduct())))
                .andExpect(jsonPath("$.productPurchaseCost", is(trackedProductDto.getProductPurchaseCost())))
                .andExpect(jsonPath("$.productSellPrice", is(trackedProductDto.getProductSellPrice())))
                .andExpect(jsonPath("$.clientCompany.id", is(trackedProductDto.getClientCompany())))
                .andDo(print());
    }

    @Test
    void givenTrackedProductsWhenPostTrackedProductWithoutAuthenticationThenStatus403() throws Exception {
        TrackedProductDto trackedProductDto = TrackedProductDto.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(String.valueOf(product4.getId()))
                .clientCompany(String.valueOf(user2.getClientCompany().getId()))
                .build();

        mockMvc.perform(post("/api/client-company/tracked-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackedProductDto)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void givenTrackedProductsWhenPostTrackedProductWithInvalidProductThenStatus400() throws Exception {
        TrackedProductDto trackedProductDto = TrackedProductDto.builder()
                .productPurchaseCost(699.00)
                .productSellPrice(1099.00)
                .product(UUID.randomUUID().toString())
                .clientCompany(String.valueOf(user2.getClientCompany().getId()))
                .build();

        mockMvc.perform(post("/api/client-company/tracked-products")
                        .with(user(user2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackedProductDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenTrackedProductsWhenGetTrackedProductsThenStatus200() throws Exception {
        mockMvc.perform(get("/api/client-company/tracked-products")
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productPurchaseCost", is(trackedProduct1.getProductPurchaseCost())))
                .andExpect(jsonPath("$[0].productSellPrice", is(trackedProduct1.getProductSellPrice())))
                .andExpect(jsonPath("$[0].product.id", is(String.valueOf(trackedProduct1.getProduct().getId()))))
                .andExpect(jsonPath("$[0].clientCompany.id", is(String.valueOf(trackedProduct1.getClientCompany().getId()))))
                .andExpect(jsonPath("$[1].productPurchaseCost", is(trackedProduct2.getProductPurchaseCost())))
                .andExpect(jsonPath("$[1].productSellPrice", is(trackedProduct2.getProductSellPrice())))
                .andExpect(jsonPath("$[1].product.id", is(String.valueOf(trackedProduct2.getProduct().getId()))))
                .andExpect(jsonPath("$[1].clientCompany.id", is(String.valueOf(trackedProduct2.getClientCompany().getId()))))
                .andExpect(jsonPath("$[2].productPurchaseCost", is(trackedProduct3.getProductPurchaseCost())))
                .andExpect(jsonPath("$[2].productSellPrice", is(trackedProduct3.getProductSellPrice())))
                .andExpect(jsonPath("$[2].product.id", is(String.valueOf(trackedProduct3.getProduct().getId()))))
                .andExpect(jsonPath("$[2].clientCompany.id", is(String.valueOf(trackedProduct3.getClientCompany().getId()))));
    }

    @Test
    void givenTrackedProductsWhenGetTrackedProductsWithoutAuthenticationThenStatus403() throws Exception {
        mockMvc.perform(get("/api/client-company/tracked-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenTrackedProductsWhenDeleteTrackedProductsThenStatus204() throws Exception {
        mockMvc.perform(delete("/api/client-company/tracked-products")
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(trackedProduct1.getId(), trackedProduct2.getId(), trackedProduct3.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void givenTrackedProductsWhenDeleteTrackedProductsWithoutAuthenticationThenStatus403() throws Exception {
        mockMvc.perform(delete("/api/client-company/tracked-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(trackedProduct1.getId(), trackedProduct2.getId(), trackedProduct3.getId()))))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenTrackedProductsWhenPutTrackedProductsThenStatus200() throws Exception {
        TrackedProductDto trackedProductDto = TrackedProductDto.builder()
                .id(String.valueOf(trackedProduct4.getId()))
                .productPurchaseCost(155.00)
                .productSellPrice(1555.00)
                .product(String.valueOf(product4.getId()))
                .clientCompany(String.valueOf(user2.getClientCompany().getId()))
                .build();

        mockMvc.perform(put("/api/client-company/tracked-products")
                        .with(user(user2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackedProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.product.id", is(trackedProductDto.getProduct())))
                .andExpect(jsonPath("$.productPurchaseCost", is(155.00)))
                .andExpect(jsonPath("$.productSellPrice", is(1555.00)))
                .andExpect(jsonPath("$.clientCompany.id", is(trackedProductDto.getClientCompany())));
    }

    @Test
    void givenTrackedProductsWhenPutTrackedProductsWithoutAuthenticationThenStatus403() throws Exception {
        TrackedProductDto trackedProductDto = TrackedProductDto.builder()
                .id(String.valueOf(trackedProduct4.getId()))
                .productPurchaseCost(155.00)
                .productSellPrice(1555.00)
                .product(String.valueOf(product4.getId()))
                .clientCompany(String.valueOf(user2.getClientCompany().getId()))
                .build();

        mockMvc.perform(put("/api/client-company/tracked-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackedProductDto)))
                .andExpect(status().isForbidden());
    }
}
