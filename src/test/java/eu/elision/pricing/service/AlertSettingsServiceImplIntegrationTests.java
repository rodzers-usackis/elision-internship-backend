package eu.elision.pricing.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertSettingsDto;
import eu.elision.pricing.repository.AlertRuleRepository;
import eu.elision.pricing.repository.AlertSettingsRepository;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import eu.elision.pricing.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class AlertSettingsServiceImplIntegrationTests {

    @Autowired
    private AlertSettingsRepository alertSettingsRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RetailerCompanyRepository retailerCompanyRepository;

    @Autowired
    private AlertSettingsServiceImpl alertSettingsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertRuleRepository alertRuleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Product product;
    private User user;
    private int numberOfAlertRulesInDbForUser1;


    @BeforeEach
    void setUp() {
        //Create user
        User user = User.builder()
            .role(Role.CLIENT)
            .firstName("John")
            .lastName("Doe")
            .email("test@test.test")
            .password("password")
            .build();

        user = userRepository.save(user);

        // Create a ClientCompany object
        ClientCompany clientCompany = ClientCompany.builder()
            .name("Client Company")
            .website("https://www.clientcompany.test")
            .build();


        this.user = user;


        // Save the ClientCompany object
        clientCompany = clientCompanyRepository.save(clientCompany);

        user.setClientCompany(clientCompany);
        user = userRepository.save(user);


        // Create NotificationSettings object
        AlertSettings alertSettings = AlertSettings.builder()
            .user(user)
            .notifyViaEmail(true)
            .alertsActive(true)
            .build();


        // Save the NotificationSettings object
//        user.setAlertSettings(alertSettings);
//        user = userRepository.save(user);
        alertSettings = alertSettingsRepository.save(alertSettings);

        // Create Products
        Product p1 = productRepository.save(Product.builder()
            .name("Product 1")
            .description("Product 1 description")
            .ean("1234567890123")
            .manufacturerCode("1234567890123")
            .category(ProductCategory.ELECTRONICS)
            .build());

        this.product = p1;

        Product p2 = productRepository.save(Product.builder()
            .name("Product 2")
            .description("Product 2 description")
            .ean("1234567890124")
            .manufacturerCode("1234567890124")
            .category(ProductCategory.ELECTRONICS)
            .build());


        // Create RetailerCompany
        RetailerCompany rc = retailerCompanyRepository.save(RetailerCompany.builder()
            .name("Retailer Company")
            .website("https://www.retailercompany.test")
            .build());

        // Create new alert rules
        AlertRule ar1 = AlertRule.builder()
            .product(p1)
            .alertSettings(alertSettings)
            .priceComparisonType(PriceComparisonType.LOWER)
            .price(500)
            .build();

        AlertRule ar2 = AlertRule.builder()
            .product(p2)
            .alertSettings(alertSettings)
            .price(999)
            .priceComparisonType(PriceComparisonType.HIGHER)
            .build();

        alertSettings.setAlertRules(List.of(ar1, ar2));
        alertSettings.setUser(user);
//        alertSettingsRepository.save(alertSettings);

        alertRuleRepository.saveAll(List.of(ar1, ar2));


        this.numberOfAlertRulesInDbForUser1 = alertSettings.getAlertRules().size();


        //create another user
        User user2 = User.builder()
            .role(Role.CLIENT)
            .firstName("John")
            .lastName("Doe")
            .email("test2@test.be")
            .password("password")
            .build();

        user2 = userRepository.save(user2);

        //create another client company
        ClientCompany clientCompany2 = ClientCompany.builder()
            .name("Client Company 2")
            .website("https://www.clientcompany2.test")
            .build();

        clientCompany2 = clientCompanyRepository.save(clientCompany2);

        user2.setClientCompany(clientCompany2);
        userRepository.save(user2);

        //create another notification settings
        AlertSettings alertSettings2 = AlertSettings.builder()
            .user(user2)
            .notifyViaEmail(true)
            .alertsActive(true)
            .build();

        user2.setAlertSettings(alertSettings2);

        alertSettings2 = alertSettingsRepository.save(alertSettings2);

        //create another alert rule
        AlertRule ar3 = AlertRule.builder()
            .product(product)
            .alertSettings(alertSettings2)
            .priceComparisonType(PriceComparisonType.LOWER)
            .price(500)
            .build();


    }

    @AfterEach
    void tearDown() {
        log.debug(">>>>> tearDown");
        clientCompanyRepository.deleteAll();
        alertSettingsRepository.deleteAll();
        userRepository.deleteAll();
        retailerCompanyRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void newAlertRuleAddedCorrectly() {

//        AlertRuleDto alertRuleDto = AlertRuleDto.builder()
//            .product(ProductDto.builder()
//                .id(product.getId())
//                .build())
//            .priceThreshold(1000)
//            .priceComparisonType(PriceComparisonType.LOWER)
//            .build();
//
//        AlertRuleDto newRule = notificationSettingsService.createAlertRule(user, alertRuleDto);
//        assertNotNull(newRule);
//        assertNotNull(newRule.getId());
//        assertEquals(numberOfAlertRulesInDbForUser1 + 1, notificationSettingsRepository
//            .findByClientCompany_IdWithAlertRules(user.getClientCompany().getId()).getAlertRules().size());
//
//


    }

    @Test
    void updateNotifyByEmailCorrectly() {

        User user = this.user;

        AlertSettingsDto alertSettingsDto = AlertSettingsDto
            .builder()
            .notifyViaEmail(false)
            .alertsActive(false)
            .build();

        alertSettingsService.updateNotificationSettings(user, alertSettingsDto);

        AlertSettingsDto notificationSettingsWithAlertRulesDto =
            alertSettingsService.getNotificationSettings(user);

        assertFalse(notificationSettingsWithAlertRulesDto.isNotifyViaEmail());
        assertFalse(notificationSettingsWithAlertRulesDto.isAlertsActive());

    }


}