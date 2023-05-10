package eu.elision.pricing.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.UserRepository;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerIntegrationTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Product product1;

    private Product product2;

    private Product product3;

    private User user;

    @BeforeAll
    void setup() {

        product1 = Product.builder()
                .name("Apple iPhone 12 Pro")
                .description("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")
                .ean("0190199731320")
                .manufacturerCode("MGMH3LL/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product1);

        product2 = Product.builder()
                .name("Apple iPhone 14 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401179")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product2);

        product3 = Product.builder()
                .name("Apple iPhone 22 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401174")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product3);

        User user = User.builder()
                .email("test@test2.com")
                .password("somepassword")
                .role(Role.CLIENT)
                .firstName("Test")
                .lastName("Test")
                .build();

        userRepository.save(user);
        this.user = user;
    }

    @Test
    void givenProductWhenPostProductsThenStatus201() throws Exception {
        mockMvc.perform(post("/api/products")
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product2)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenProductWhenGetProductsThenStatus200() throws Exception {

        mockMvc.perform(get("/api/products")
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Apple iPhone 12 Pro")))
                .andExpect(jsonPath("$[0].description", is("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")))
                .andExpect(jsonPath("$[0].ean", is("0190199731320")))
                .andExpect(jsonPath("$[0].manufacturerCode", is("MGMH3LL/A")))
                .andExpect(jsonPath("$[0].category", is("ELECTRONICS")))
                .andExpect(jsonPath("$[1].name", is("Apple iPhone 14 Pro")))
                .andExpect(jsonPath("$[1].description", is("128GB, Spacezwart")))
                .andExpect(jsonPath("$[1].ean", is("194253401179")))
                .andExpect(jsonPath("$[1].manufacturerCode", is("MPXV3ZD/A")))
                .andExpect(jsonPath("$[1].category", is("ELECTRONICS")));
    }

    @Test
    void givenProductWhenGetProductByIdThenStatus200() throws Exception {

        mockMvc.perform(get("/api/products/{uuid}", product2.getId().toString())
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product2.getName())))
                .andExpect(jsonPath("$.description", is(product2.getDescription())))
                .andExpect(jsonPath("$.ean", is(product2.getEan())))
                .andExpect(jsonPath("$.manufacturerCode", is(product2.getManufacturerCode())))
                .andExpect(jsonPath("$.category", is(product2.getCategory().name())));
    }

    @Test
    void givenInvalidProductWhenGetProductByIdThenStatus404() throws Exception {

        mockMvc.perform(get("/api/products/{uuid}", UUID.randomUUID().toString())
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenProductsWhenDeleteProductThenStatus204() throws Exception {

        mockMvc.perform(delete("/api/products/{uuid}", product1.getId())
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenProductsWhenDeleteProductThenStatus404() throws Exception {

        mockMvc.perform(delete("/api/products/{uuid}", UUID.randomUUID().toString())
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenProductsWhenPutProductThenStatus204() throws Exception {
            product3.setName("New Name");

            mockMvc.perform(put("/api/products")
                            .with(user(user))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(product3)))
                    .andExpect(status().isNoContent());
    }

    @Test
    void givenProductsWhenPutInvalidProductThenStatus404() throws Exception {
        Product tempProduct = Product.builder()
                .id(UUID.randomUUID())
                .name("Apple iPhone 14 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401179")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        mockMvc.perform(put("/api/products")
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tempProduct)))
                .andExpect(status().isNotFound());
    }

    @AfterAll
    void cleanUp() {
        productRepository.deleteAll();
        userRepository.deleteAll();
    }
}
