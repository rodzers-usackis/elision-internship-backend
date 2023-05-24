package eu.elision.pricing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.AlertRepository;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class AlertServiceImplTests {

    @Autowired
    private AlertService alertService;

    @MockBean
    private AlertRepository alertRepository;

    @Captor
    private ArgumentCaptor<Alert> alertCaptor;

    @Test
    void createAlertsSuccessfully() {

        Product product = Product.builder()
                .name("Test product")
                .id(UUID.randomUUID())
                .category(ProductCategory.ELECTRONICS)
                .manufacturerCode("123A")
                .ean("1234567890123")
                .description("Test description")
                .build();

        ClientCompany clientCompany = ClientCompany.builder()
                .VATNumber("BE121345678992")
                .id(UUID.randomUUID())
                .name("Test company")
                .build();

        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("test@testuser.be")
                .clientCompany(clientCompany)
                .build();

        clientCompany.setUsers(List.of(user));

        AlertSettings alertSettings = AlertSettings.builder()
                .id(UUID.randomUUID())
                .notifyViaEmail(true)
                .user(user)
                .build();

        AlertRule alertRule1 = AlertRule.builder()
                .id(UUID.randomUUID())
                .product(product)
                .priceComparisonType(PriceComparisonType.LOWER)
                .price(100.0)
                .alertSettings(alertSettings)
                .build();

        AlertRule alertRule2 = AlertRule.builder()
                .id(UUID.randomUUID())
                .product(product)
                .priceComparisonType(PriceComparisonType.HIGHER)
                .price(5000.0)
                .alertSettings(alertSettings)
                .build();

        AlertRule alertRule3 = AlertRule.builder()
                .id(UUID.randomUUID())
                .product(product)
                .priceComparisonType(PriceComparisonType.HIGHER)
                .price(200.0)
                .alertSettings(alertSettings)
                .build();

        product.setAlertRules(List.of(alertRule1, alertRule2, alertRule3));

        Price price1 = Price.builder()
                .id(UUID.randomUUID())
                .product(product)
                .retailerCompany(null)
                .amount(50.0)
                .build();

        Price price2 = Price.builder()
                .id(UUID.randomUUID())
                .product(product)
                .retailerCompany(null)
                .amount(1000.0)
                .build();

        alertSettings.setAlertRules(List.of(alertRule1, alertRule2, alertRule3));


        alertService.createAlerts(product, List.of(price1, price2));


        verify(alertRepository, times(2)).save(alertCaptor.capture());

        List<Alert> capturedAlerts = alertCaptor.getAllValues();

        assertEquals(2, capturedAlerts.size());
        assertEquals(user, capturedAlerts.get(0).getUser());
        assertEquals(user, capturedAlerts.get(1).getUser());
        assertEquals(product, capturedAlerts.get(0).getProduct());
        assertEquals(product, capturedAlerts.get(1).getProduct());
        assertEquals(price1, capturedAlerts.get(0).getPrice());
        assertEquals(price2, capturedAlerts.get(1).getPrice());


    }

    @Test
    void noAlertsCreated() {

        Product product = Product.builder()
                .name("Test product")
                .id(UUID.randomUUID())
                .category(ProductCategory.ELECTRONICS)
                .manufacturerCode("123A")
                .ean("1234567890123")
                .description("Test description")
                .build();

        ClientCompany clientCompany = ClientCompany.builder()
                .VATNumber("BE123456789")

                .id(UUID.randomUUID())
                .name("Test company")
                .build();

        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("test@testuser2.be")
                .clientCompany(clientCompany)
                .build();

        clientCompany.setUsers(List.of(user));


        AlertSettings alertSettings = AlertSettings.builder()
                .id(UUID.randomUUID())
                .notifyViaEmail(true)
                .user(user)
                .build();

        AlertRule alertRule1 = AlertRule.builder()
                .id(UUID.randomUUID())
                .product(product)
                .priceComparisonType(PriceComparisonType.LOWER)
                .price(500)
                .alertSettings(alertSettings)
                .build();

        AlertRule alertRule2 = AlertRule.builder()
                .id(UUID.randomUUID())
                .product(product)
                .priceComparisonType(PriceComparisonType.HIGHER)
                .price(5000.0)
                .alertSettings(alertSettings)
                .build();

        AlertRule alertRule3 = AlertRule.builder()
                .id(UUID.randomUUID())
                .product(product)
                .priceComparisonType(PriceComparisonType.LOWER)
                .price(200.0)
                .alertSettings(alertSettings)
                .build();

        product.setAlertRules(List.of(alertRule1, alertRule2, alertRule3));

        Price price1 = Price.builder()
                .id(UUID.randomUUID())
                .product(product)
                .retailerCompany(null)
                .amount(2000.0)
                .build();

        Price price2 = Price.builder()
                .id(UUID.randomUUID())
                .product(product)
                .retailerCompany(null)
                .amount(1000.0)
                .build();


        verify(alertRepository, times(0)).save(alertCaptor.capture());
    }


}