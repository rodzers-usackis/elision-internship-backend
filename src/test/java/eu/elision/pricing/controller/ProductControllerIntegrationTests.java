package eu.elision.pricing.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.repository.ProductRepository;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Product product1;

    private Product product2;

    private UUID realUUID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15");

    private final UUID randomUUID = UUID.randomUUID();

    @BeforeAll
    void setup() throws JsonProcessingException {

        product1 = Product.builder()
                .name("Apple iPhone 12 Pro")
                .description("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")
                .ean("0190199731320")
                .manufacturerCode("MGMH3LL/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product1);

        realUUID = product1.getId();

        product2 = Product.builder()
                .id(realUUID)
                .name("Apple iPhone 14 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401179")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product2);
    }

    @Test
    void givenProductWhenGetProductsThenStatus200() throws Exception {

        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(21)))
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

    /**
    @Test
    void givenProductWhenGetProductByIdThenStatus200() throws Exception {

        mockMvc.perform(get("/api/products/" + realUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product1.getName())))
                .andExpect(jsonPath("$.description", is(product1.getDescription())))
                .andExpect(jsonPath("$.ean", is(product1.getEan())))
                .andExpect(jsonPath("$.manufacturerCode", is(product1.getManufacturerCode())))
                .andExpect(jsonPath("$.category", is(product1.getCategory().name())));
    }
    */

    @Test
    void givenInvalidProductWhenGetProductByIdThenStatus404() throws Exception {

        mockMvc.perform(get("/api/products/{uuid}", randomUUID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenProductsWhenDeleteProductThenStatus204() throws Exception {

        mockMvc.perform(delete("/api/products/" + realUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenProductsWhenDeleteProductThenStatus404() throws Exception {

        mockMvc.perform(delete("/api/products/{uuid}", product1.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
