package eu.elision.pricing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.elision.pricing.domain.*;
import eu.elision.pricing.events.ProductsPricesScrapedEvent;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;


import java.util.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class EmailControllerTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private final static Logger logger = LoggerFactory.getLogger(EmailControllerTests.class);

//    @Test
//    void givenPostRequestSendOutEmailsWhenScrapingIsDoneReturns200() throws Exception {
//        AlertSettings alertSettings = AlertSettings.builder()
//            .notifyViaEmail(true)
//            .alertsActive(true)
//            .alertStorageDuration(30)
//            .build();
//
//        // Users
//        final User user = User.builder()
//            .email("rodzers.usackis@student.kdg.be")
//            .password("secure_password")
//            .firstName("John")
//            .lastName("Smith")
//            .role(Role.ADMIN)
//            .alertSettings(alertSettings)
//            .build();
//
//        alertSettings.setEmailAddress(user.getEmail());
//        alertSettings.setUser(user);
//
//        // Addresses
//        Address address = Address.builder()
//            .street("Main Street")
//            .streetNumber("123")
//            .apartmentNumber("1")
//            .city("New York")
//            .postalCode("10001")
//            .country("USA")
//            .build();
//
//
//        // Products
//        Product product1 = Product.builder()
//            .id(UUID.randomUUID())
//            .name("Apple iPhone 12 Pro")
//            .description("Apple iPhone 12")
//            .ean("0190199731320")
//            .manufacturerCode("MGMH3LL/A")
//            .category(ProductCategory.ELECTRONICS)
//            .build();
//
//        ClientCompany clientCompany = ClientCompany.builder()
//            .address(address)
//            .name("Elision")
//            .website("https://elision.eu")
//            .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.ELECTRONICS)))
//            .build();
//
//        TrackedProduct trackedProduct1 = TrackedProduct.builder()
//            .productPurchaseCost(699.00)
//            .productSellPrice(1099.00)
//            .isTracked(true)
//            .product(product1)
//            .clientCompany(clientCompany)
//            .build();
//
//        clientCompany.setTrackedProducts(new ArrayList<>(List.of(trackedProduct1)));
//        user.setClientCompany(clientCompany);
//
//        Price price1 = Price.builder()
//            .id(UUID.randomUUID())
//            .amount(100.0)
//            .product(product1)
//            .build();
//
//        Price price2 = Price.builder()
//            .id(UUID.randomUUID())
//            .amount(200.0)
//            .product(product1)
//            .build();
//
//        Map<UUID, List<UUID>> productToPricesMap =
//            Map.of(product1.getId(), List.of(price1.getId(), price2.getId()));
//
//        ProductsPricesScrapedEvent productsPricesScrapedEvent =
//            new ProductsPricesScrapedEvent(productToPricesMap);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        logger.info(objectMapper.writeValueAsString(productsPricesScrapedEvent));
//
//        mockMvc.perform(post("/api/sendOutEmails")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productsPricesScrapedEvent)))
//            .andExpect(status().isOk());
//    }
}
