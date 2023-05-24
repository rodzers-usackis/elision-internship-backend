//package eu.elision.pricing.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import eu.elision.pricing.domain.AlertRule;
//import eu.elision.pricing.domain.ClientCompany;
//import eu.elision.pricing.domain.AlertSettings;
//import eu.elision.pricing.domain.PriceComparisonType;
//import eu.elision.pricing.domain.Product;
//import eu.elision.pricing.domain.ProductCategory;
//import eu.elision.pricing.domain.RetailerCompany;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import java.util.List;
//import java.util.UUID;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//
//@Slf4j
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DataJpaTest
//class AlertSettingsRepositoryTests {
//
//    @Autowired
//    private AlertSettingsRepository alertSettingsRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private RetailerCompanyRepository retailerCompanyRepository;
//
//    @Autowired
//    private ClientCompanyRepository clientCompanyRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private static UUID CLIENT_COMPANY_ID;
//
//    @BeforeAll
//    void setUp() {
//        // Create a ClientCompany object
////        ClientCompany clientCompany = ClientCompany.builder()
////            .name("Client Company")
////            .website("https://www.clientcompany.test")
////            .build();
////
////
////        // Save the ClientCompany object
////        clientCompanyRepository.save(clientCompany);
////        CLIENT_COMPANY_ID = clientCompany.getId();
////        log.debug(">>> ClientCompany ID: {}", CLIENT_COMPANY_ID);
////
////
////
////        //TODO: add user to clientCompany
////
////        // Create NotificationSettings object
////        AlertSettings alertSettings = AlertSettings.builder()
//////            .user()
////            .notifyViaEmail(true)
////            .build();
////
////
////        clientCompany.setAlertSettings(alertSettings);
//
//        // Save the NotificationSettings object
//        alertSettingsRepository.save(alertSettings);
//
//        log.debug(">>> NotificationSettings ID: {}", alertSettings.getId());
//
//        // Create Products
//        Product p1 = productRepository.save(Product.builder()
//            .name("Product 1")
//            .description("Product 1 description")
//            .ean("1234567890123")
//            .manufacturerCode("1234567890123")
//            .category(ProductCategory.CONSUMER_ELECTRONICS)
//            .build());
//
//        Product p2 = productRepository.save(Product.builder()
//            .name("Product 2")
//            .description("Product 2 description")
//            .ean("1234567890124")
//            .manufacturerCode("1234567890124")
//            .category(ProductCategory.CONSUMER_ELECTRONICS)
//            .build());
//
//
//        // Create RetailerCompany
//        RetailerCompany rc = retailerCompanyRepository.save(RetailerCompany.builder()
//            .name("Retailer Company")
//            .website("https://www.retailercompany.test")
//            .build());
//
//        // Create new alert rules
//        AlertRule ar1 = AlertRule.builder()
//            .product(p1)
//            .alertSettings(alertSettings)
//            .priceComparisonType(PriceComparisonType.LOWER)
//            .price(500)
//            .build();
//
//        AlertRule ar2 = AlertRule.builder()
//            .product(p2)
//            .alertSettings(alertSettings)
//            .price(999)
//            .priceComparisonType(PriceComparisonType.HIGHER)
//            .build();
//
//        alertSettings.setAlertRules(List.of(ar1, ar2));
//        alertSettingsRepository.save(alertSettings);
//
//
//    }
//
//    @Test
//    void dontCauseNplusOneProblemWhileFetchingAlertRules() {
////
////        entityManager
////            .unwrap(org.hibernate.Session.class)
////            .getSessionFactory()
////            .getStatistics()
////            .setStatisticsEnabled(true);
////
////
////        AlertSettings ns =
////            notificationSettingsRepository.findByClientCompany_IdWithAlertRules(CLIENT_COMPANY_ID);
////
////        log.debug(">>> ClientCompany ID: {}", CLIENT_COMPANY_ID);
////
////        long queryCount = entityManager
////            .unwrap(org.hibernate.Session.class)
////            .getSessionFactory()
////            .getStatistics()
////            .getPrepareStatementCount();
////
////
////        //data present
////        assertEquals(2, ns.getAlertRules().size());
////        // Assert the number of queries
////        assertTrue(queryCount <= 2);
//
//    }
//
//
//}