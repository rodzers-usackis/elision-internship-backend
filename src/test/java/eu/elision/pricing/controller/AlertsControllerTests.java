package eu.elision.pricing.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.elision.pricing.domain.Address;
import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertDto;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.dto.RetailerCompanyDto;
import eu.elision.pricing.service.AlertService;
import eu.elision.pricing.util.LocalDateTimeAdapter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class AlertsControllerTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AlertService alertService;

    private final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .create();

    @Test
    void getAlertsCorrectlyForTheAuthenticatedUser() throws Exception {

        ClientCompany clientCompany = ClientCompany.builder()
            .name("Test Company")
            .vatNumber("BE1234567899")
            .id(UUID.randomUUID())
            .build();

        User user = User.builder()
            .firstName("John")
            .lastName("Doe")
            .role(Role.CLIENT)
            .email("test@email.com.be")
            .id(UUID.randomUUID())
            .clientCompany(clientCompany)
            .build();

        ProductDto product = ProductDto.builder()
            .name("Test Product")
            .id(UUID.randomUUID())
            .build();

        RetailerCompanyDto retailerCompany = RetailerCompanyDto.builder()
            .name("Test Retailer")
            .id(UUID.randomUUID())
            .build();


        AlertDto alertDto1 = AlertDto.builder()
            .id(UUID.randomUUID())
            .read(false)
            .timestamp(LocalDateTime.now().minusDays(10))
            .product(product)
            .retailerCompany(retailerCompany)
            .build();

        AlertDto alertDto2 = AlertDto.builder()
            .id(UUID.randomUUID())
            .read(true)
            .timestamp(LocalDateTime.now().minusDays(20))
            .product(product)
            .retailerCompany(retailerCompany)
            .build();

        when(alertService.getUsersAlerts(user)).thenReturn(List.of(alertDto1, alertDto2));

        mockMvc.perform(get("/api/alerts")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(alertDto1.getId().toString()))
            .andExpect(jsonPath("$[0].read").value(alertDto1.isRead()))
            .andExpect(jsonPath("$[0].product.id").value(alertDto1.getProduct().getId().toString()))
            .andExpect(jsonPath("$[1].product.name").value(alertDto2.getProduct().getName()))
            .andExpect(jsonPath("$[1].retailerCompany.id").value(
                alertDto2.getRetailerCompany().getId().toString()))
            .andExpect(jsonPath("$[1].retailerCompany.name").value(
                alertDto2.getRetailerCompany().getName()));

    }

    @Test
    void givenGetUnreadAlertCountForTheAuthenticatedUser() throws Exception {

        Product product = Product.builder()
            .name("Apple iPhone 12 Pro")
            .description("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")
            .ean("01901997313220")
            .manufacturerCode("MGMH3LL/A")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        Address address = Address.builder()
            .streetAddress("Main Street")
            .streetNumber("123")
            .apartmentNumber("1")
            .city("New York")
            .zipCode("10001")
            .country("USA")
            .build();

        ClientCompany clientCompany = ClientCompany.builder()
            .address(address)
            .vatNumber("BE1234567819")
            .name("Elision")
            .website("https://elision.eu")
            .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.CONSUMER_ELECTRONICS)))
            .build();

        User user = User.builder()
            .firstName("John")
            .lastName("Doe")
            .role(Role.CLIENT)
            .email("joh1n.doe@gmail.com")
            .build();

        clientCompany.setUsers((List.of(user)));
        user.setClientCompany(clientCompany);

        RetailerCompany retailerCompany = RetailerCompany.builder()
            .name("Amazon")
            .website("www.amazon.com")
            .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.CONSUMER_ELECTRONICS)))
            .build();

        Alert alert1 = Alert.builder()
            .id(UUID.randomUUID())
            .read(false)
            .timestamp(LocalDateTime.now().minusDays(10))
            .user(user)
            .retailerCompany(retailerCompany)
            .product(product)
            .retailerCompany(retailerCompany)
            .product(product)
            .build();

        Alert alert2 = Alert.builder()
            .id(UUID.randomUUID())
            .read(false)
            .timestamp(LocalDateTime.now().minusDays(10))
            .user(user)
            .retailerCompany(retailerCompany)
            .product(product)
            .retailerCompany(retailerCompany)
            .product(product)
            .build();

        Alert alert3 = Alert.builder()
            .id(UUID.randomUUID())
            .read(true)
            .timestamp(LocalDateTime.now().minusDays(10))
            .user(user)
            .retailerCompany(retailerCompany)
            .product(product)
            .retailerCompany(retailerCompany)
            .product(product)
            .build();

        when(alertService.getUnreadAlertCount(user)).thenReturn(2);

        mockMvc.perform(get("/api/alerts/unread/count")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(2));
    }

    @Test
    void givenMarkAlertAsReadForTheAuthenticatedUser() throws Exception {

        ProductDto product = ProductDto.builder()
            .name("Test Product")
            .id(UUID.randomUUID())
            .build();

        Address address = Address.builder()
            .streetAddress("Main Street")
            .streetNumber("123")
            .apartmentNumber("1")
            .city("New York")
            .zipCode("10001")
            .country("USA")
            .build();

        ClientCompany clientCompany = ClientCompany.builder()
            .address(address)
            .vatNumber("BE01233456789")
            .name("Elision")
            .website("https://elision.eu")
            .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.CONSUMER_ELECTRONICS)))
            .build();

        User user = User.builder()
            .firstName("John")
            .lastName("Doe")
            .role(Role.CLIENT)
            .email("joh1n.doe@gmail.com")
            .build();

        clientCompany.setUsers((List.of(user)));
        user.setClientCompany(clientCompany);

        RetailerCompanyDto retailerCompany = RetailerCompanyDto.builder()
            .name("Test Retailer")
            .id(UUID.randomUUID())
            .build();

        AlertDto alertDto1 = AlertDto.builder()
            .id(UUID.randomUUID())
            .read(true)
            .timestamp(LocalDateTime.now().minusDays(10))
            .product(product)
            .retailerCompany(retailerCompany)
            .build();

        AlertDto alertDto2 = AlertDto.builder()
            .id(UUID.randomUUID())
            .read(true)
            .timestamp(LocalDateTime.now().minusDays(20))
            .product(product)
            .retailerCompany(retailerCompany)
            .build();

        when(alertService.markAlertsAsRead(List.of(alertDto1, alertDto2))).thenReturn(
            List.of(alertDto1, alertDto2));

        mockMvc.perform(patch("/api/alerts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(user))
                .content(gson.toJson(List.of(alertDto1, alertDto2))))
            .andDo(result -> log.debug(">>>> " + result.getResponse().getContentAsString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].read").value(true))
            .andExpect(jsonPath("$[1].read").value(true));
    }
}