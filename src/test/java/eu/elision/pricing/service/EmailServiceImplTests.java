package eu.elision.pricing.service;

import eu.elision.pricing.domain.*;
import eu.elision.pricing.events.ScrapingFinishedEvent;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
public class EmailServiceImplTests {

    @Autowired
    private EmailService emailService;

    @Test
    void sendOutEmailsSuccessfully() {
        Product product = Product.builder()
            .name("Test product")
            .id(UUID.randomUUID())
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .manufacturerCode("123A")
            .ean("1234567890123")
            .description("Test description")
            .build();

        ClientCompany clientCompany = ClientCompany.builder()
            .VATNumber("BE12134567899")
            .id(UUID.randomUUID())
            .name("Test company")
            .build();

        User user = User.builder()
            .id(UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .email("rodzers.usackis@student.kdg.be")
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

        Map<UUID, List<UUID>> productToPricesMap =
            Map.of(product.getId(), List.of(price1.getId(), price2.getId()));


        emailService.sendEventAfterPriceScraping(LocalDateTime.now());
    }
}
