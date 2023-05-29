package eu.elision.pricing.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CascadeTest {

    @Autowired
    private AlertRuleRepository alertRuleRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RetailerCompanyRepository retailerCompanyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AlertSettingsRepository alertSettingsRepository;


    @BeforeEach
    void setUp() {

        AlertSettings alertSettings = AlertSettings.builder()
            .alertsActive(true)
            .notifyViaEmail(true)
            .alertStorageDuration(60)
            .build();

        User user = User.builder()
            .role(Role.ADMIN)
            .email("testuser@test.be")
            .password("test")
            .firstName("test")
            .lastName("test")
            .alertSettings(alertSettings)
            .build();

        alertSettings.setUser(user);

        user = userRepository.save(user);


        ClientCompany clientCompany = ClientCompany.builder()
            .name("test")
            .website("test.be")
            .build();

        clientCompany = clientCompanyRepository.save(clientCompany);
        clientCompany.setUsers(List.of(user));
        clientCompany = clientCompanyRepository.save(clientCompany);

        RetailerCompany retailerCompany = RetailerCompany.builder()
            .name("test")
            .website("test.be")
            .build();

        retailerCompany = retailerCompanyRepository.save(retailerCompany);

        Product product = Product.builder()
            .name("test")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .description("test")
            .manufacturerCode("test")
            .ean("test")
            .build();

        product = productRepository.save(product);

        Alert alert = Alert.builder()
            .retailerCompany(retailerCompany)
            .product(product)
            .user(user)
            .build();

        alert = alertRepository.save(alert);

        Alert alert2 = Alert.builder()
            .retailerCompany(retailerCompany)
            .product(product)
            .user(user)
            .build();

        alert2 = alertRepository.save(alert2);


        alertSettings = user.getAlertSettings();

        AlertRule alertRule = AlertRule.builder()
            .priceComparisonType(PriceComparisonType.LOWER)
            .product(product)
            .alertSettings(alertSettings)
            .build();

        alertSettings.setAlertRules(List.of(alertRule));
        alertRule = alertRuleRepository.save(alertRule);


    }

    @AfterEach
    void tearDown() {
        alertRepository.deleteAll();
        alertRuleRepository.deleteAll();
        clientCompanyRepository.deleteAll();
        retailerCompanyRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void entitiesDeletedCorrectlyWhenUserDeleted() {


        assertEquals(1, userRepository.count());
        assertEquals(2, alertRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, alertSettingsRepository.count());
        assertEquals(1, alertRuleRepository.count());

        var user = userRepository.findAll().get(0);
        userRepository.delete(user);

        assertEquals(0, userRepository.count());
        assertEquals(0, alertRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(0, alertSettingsRepository.count());
        assertEquals(0, alertRuleRepository.count());


    }

    @Test
    void nothingElseDeletedWhenDeletingAlertRule() {

        assertEquals(1, userRepository.count());
        assertEquals(2, alertRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, alertSettingsRepository.count());
        assertEquals(1, alertRuleRepository.count());

        var alertRule = alertRuleRepository.findAll().get(0);
        alertRuleRepository.delete(alertRule);

        assertEquals(1, userRepository.count());
        assertEquals(2, alertRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, alertSettingsRepository.count());
        assertEquals(0, alertRuleRepository.count());


    }

}