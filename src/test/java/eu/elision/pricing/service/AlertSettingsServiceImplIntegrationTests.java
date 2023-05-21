package eu.elision.pricing.service;

import static org.junit.jupiter.api.Assertions.*;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.dto.notifications.AlertRuleDto;
import eu.elision.pricing.dto.notifications.NotificationSettingsWithAlertRulesDto;
import eu.elision.pricing.dto.notifications.NotificationSettingsDto;
import eu.elision.pricing.repository.AlertRuleRepository;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.NotificationSettingsRepository;
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
    private NotificationSettingsRepository notificationSettingsRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RetailerCompanyRepository retailerCompanyRepository;

    @Autowired
    private NotificationSettingsServiceImpl notificationSettingsService;

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
        userRepository.save(user);


        // Create NotificationSettings object
        AlertSettings alertSettings = AlertSettings.builder()
            .clientCompany(clientCompany)
            .notifyViaEmail(true)
            .build();


        clientCompany.setAlertSettings(alertSettings);

        // Save the NotificationSettings object
        notificationSettingsRepository.save(alertSettings);


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

        alertRuleRepository.saveAll(List.of(ar1, ar2));

        alertSettings.setAlertRules(List.of(ar1, ar2));
        notificationSettingsRepository.save(alertSettings);

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
            .clientCompany(clientCompany2)
            .notifyViaEmail(true)
            .build();

        clientCompany2.setAlertSettings(alertSettings2);

        alertSettings2 = notificationSettingsRepository.save(alertSettings2);

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
        notificationSettingsRepository.deleteAll();
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
    void getNotificationSettingsForTheUserCorrectly() {

        NotificationSettingsWithAlertRulesDto notificationSettingsWithAlertRulesDto =
            notificationSettingsService.getNotificationSettings(user);

        assertNotNull(notificationSettingsWithAlertRulesDto);
        assertNotNull(notificationSettingsWithAlertRulesDto.getAlertRules());
        assertEquals(numberOfAlertRulesInDbForUser1,
            notificationSettingsWithAlertRulesDto.getAlertRules().size());
        assertTrue(notificationSettingsWithAlertRulesDto.isNotifyViaEmail());

    }

    @Test
    void updateNotifyByEmailCorrectly() {

        User user = this.user;

        NotificationSettingsDto notificationSettingsDto =
            NotificationSettingsDto.builder()
                .notifyViaEmail(false)
                .build();

        notificationSettingsService.updateNotificationSettings(user, notificationSettingsDto);

        NotificationSettingsWithAlertRulesDto notificationSettingsWithAlertRulesDto =
            notificationSettingsService.getNotificationSettings(user);

        assertFalse(notificationSettingsWithAlertRulesDto.isNotifyViaEmail());

    }

//    @Test
//    void alertRulesDeletedCorrectly() {
//
//        UUID clientCompanyId = user.getClientCompany().getId();
//        log.debug(">>>> clientCompanyId {}", clientCompanyId);
//
//        List<UUID> alertRuleIds =
//            notificationSettingsRepository.findByClientCompany_Id(clientCompanyId)
//                .getAlertRules().stream().map(AlertRule::getId).toList();
//        log.debug(">>>> alertRuleIds {}", alertRuleIds);
//
//
//        log.debug(">>>>> ns id: {}",
//            notificationSettingsRepository.findByClientCompany_Id(clientCompanyId).getId()
//                .toString());
//
//        assertTrue(clientCompanyRepository.findById(clientCompanyId).isPresent());
//        assertNotNull(notificationSettingsRepository.findByClientCompany_Id(clientCompanyId));
////        assertNotNull(notificationSettingsRepository.findByClientCompany_Id(clientCompanyId)
////            .getAlertRules());
//        assertTrue(userRepository.findByEmail(user.getEmail()).isPresent());
//
//
//        notificationSettingsService.deleteAlertRules(user, alertRuleIds);
//
//        assertTrue(clientCompanyRepository.findById(clientCompanyId).isPresent());
//
//        assertTrue(userRepository.findByEmail(user.getEmail()).isPresent());
//        assertEquals(1, notificationSettingsRepository.findAll().size());
//        assertNotNull(notificationSettingsRepository.findByClientCompany_Id(clientCompanyId));
////        assertNotNull(notificationSettingsRepository.findByClientCompany_Id(clientCompanyId)
////            .getAlertRules());
//
////        log.debug(">>>>> user ns:{}", user.getClientCompany().getNotificationSettings() == null
////            ? "null" : user.getClientCompany().getNotificationSettings().getId().toString());
//
////        log.debug(">>>>> ns id: {}",
////            notificationSettingsRepository.findByClientCompany_Id(clientCompanyId).getId()
////                .toString());
//        assertEquals(0,
//            notificationSettingsRepository.findByClientCompany_Id(clientCompanyId)
//                .getAlertRules().size());
//
//
//    }
//
//    @Test
//    void alertRulesDeletedCorrectly2() {
//
//        UUID clientCompanyId = user.getClientCompany().getId();
//        log.debug(">>>> clientCompanyId {}", clientCompanyId);
//
//        List<UUID> alertRuleIds = notificationSettingsRepository
//            .findByClientCompany_Id(clientCompanyId)
//            .getAlertRules()
//            .stream()
//            .map(AlertRule::getId)
//            .toList();
//        log.debug(">>>> alertRuleIds {}", alertRuleIds);
//
//        log.debug(">>>>> ns id: {}",
//            notificationSettingsRepository.findByClientCompany_Id(clientCompanyId)
//                .getId()
//                .toString());
//
//        notificationSettingsService.deleteAlertRules(user, alertRuleIds);
//
//        // Detach and reattach the NotificationSettings entity
//        entityManager.detach(user.getClientCompany().getNotificationSettings());
//        NotificationSettings notificationSettings = notificationSettingsRepository
//            .findByClientCompany_Id(clientCompanyId);
//
//        log.debug(">>>>> ns id: {}",
//            notificationSettings.getId().toString());
//
//        assertEquals(0, notificationSettings.getAlertRules().size());
//    }
//
//    @Test
//    void test1() {
//        log.debug(">>>> test {}",
//            alertRuleRepository.countAllByIdAndNotificationSettingsClientCompany_Id(
//                alertRuleRepository.findAll().stream().map(AlertRule::getId)
//                    .collect(Collectors.toList()), user.getClientCompany().getId()));
//    }

}