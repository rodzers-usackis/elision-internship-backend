package eu.elision.pricing.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasLength;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import eu.elision.pricing.domain.Address;
import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertRuleToCreateDto;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.repository.AlertRepository;
import eu.elision.pricing.repository.AlertRuleRepository;
import eu.elision.pricing.repository.AlertSettingsRepository;
import eu.elision.pricing.repository.ClientCompanyRepository;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import eu.elision.pricing.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class AlertRuleControllerTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertRuleRepository alertRuleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RetailerCompanyRepository retailerCompanyRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Autowired
    private TrackedProductRepository trackedProductRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private PriceScrapingConfigRepository priceScrapingConfigRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private AlertSettingsRepository alertSettingsRepository;

    @Autowired
    private MockMvc mockMvc;

    private AlertRule alertRuleToDelete;


    @BeforeEach
    void setUp() {

        AlertSettings alertSettings = AlertSettings.builder()
            .notifyViaEmail(true)
            .alertsActive(true)
            .alertStorageDuration(30)
            .build();

        // Users
        User user = User.builder()
            .email("test@test.test")
            .password("passworddddd")
            .firstName("Test")
            .lastName("Test")
            .role(Role.CLIENT)
            .alertSettings(alertSettings)
            .build();

        alertSettings.setEmailAddress(user.getEmail());
        alertSettings.setUser(user);

        // Addresses
        Address address = Address.builder()
            .streetAddress("Main Street")
            .streetNumber("123")
            .apartmentNumber("1")
            .city("New York")
            .zipCode("10001")
            .country("USA")
            .build();


        // Products
        Product product1 = Product.builder()
            .name("Apple iPhone 12 Pro")
            .description("Apple iPhone 12 Pro 128GB, Graphite - Fully Unlocked (Renewed)")
            .ean("0190199731320")
            .manufacturerCode("MGMH3LL/A")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product1);

        Product product2 = Product.builder()
            .name("Apple iPhone 14 Pro")
            .description("128GB, Spacezwart")
            .ean("194253401179")
            .manufacturerCode("MPXV3ZD/A")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product2);

        Product product3 = Product.builder()
            .name("Apple iPhone 14")
            .description("128GB, Middernacht")
            .ean("194253408253")
            .manufacturerCode("MPUF3ZD/A")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product3);

        Product product4 = Product.builder()
            .name("Apple iPhone 13")
            .description("128GB, Black")
            .ean("194252707289")
            .manufacturerCode("MLPF3ZD/A")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product4);

        Product product5 = Product.builder()
            .name("Apple iPhone 12")
            .description("128GB, Black")
            .ean("194252031315")
            .manufacturerCode("MGJA3ZD/A")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product5);

        Product product6 = Product.builder()
            .name("Samsung Galaxy S23 Ultra 5G")
            .description("512GB, Phantom Black")
            .ean("8806094729207")
            .manufacturerCode("SM-S918BZKHEUB")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product6);

        Product product7 = Product.builder()
            .name("Samsung Galaxy S22 Ultra 5G")
            .description("128GB, Phantom Black")
            .ean("8806092879140")
            .manufacturerCode("SM-S908BZKDEUB")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product7);

        Product product8 = Product.builder()
            .name("Samsung Galaxy Z Fold4")
            .description("256GB, Phantom Black")
            .ean("8806094504682")
            .manufacturerCode("SM-F936BZKBEUB")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product8);

        Product product9 = Product.builder()
            .name("Samsung Galaxy A54 5G")
            .description("Samsung Galaxy A54 5G 128 GB Awesome Graphite")
            .ean("8806094885699")
            .manufacturerCode("SM-A546BZKCEUB")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product9);

        Product product10 = Product.builder()
            .name("Samsung Galaxy A34 5G")
            .description("Samsung Galaxy A34 5G 128 GB Awesome Graphite")
            .ean("8806094813845")
            .manufacturerCode("SM-A346BZKAEUB")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product10);

        Product product11 = Product.builder()
            .name("Samsung Galaxy A23 5G")
            .description("Samsung Galaxy A23 5G 128 GB Awesome Black")
            .ean("8806094534047")
            .manufacturerCode("SM-A236BZKVEUB")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product11);

        Product product12 = Product.builder()
            .name("OnePlus Nord 2T")
            .description("OnePlus Nord 2T Grey Shadow 128GB")
            .ean("6921815621331")
            .manufacturerCode("5011102071")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product12);

        Product product13 = Product.builder()
            .name("OnePlus Nord CE 2 Lite")
            .description("OnePlus Nord CE 2 Lite Black 128GB 6GB 5G")
            .ean("6921815620716")
            .manufacturerCode("5011102002")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product13);

        Product product14 = Product.builder()
            .name("OnePlus 9")
            .description("OnePlus 9 Astral Black 128GB")
            .ean("6921815615378")
            .manufacturerCode("5011101552")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product14);

        Product product15 = Product.builder()
            .name("OnePlus Nord")
            .description("OnePlus Nord Blue 128GB")
            .ean("6921815611752")
            .manufacturerCode("5011101199")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product15);

        Product product16 = Product.builder()
            .name("Xiaomi Redmi Note 11 Pro")
            .description("Xiaomi Redmi Note 11 Pro 128 GB Graphite Gray")
            .ean("6934177769733")
            .manufacturerCode("37969")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product16);

        Product product17 = Product.builder()
            .name("Xiaomi 12 5G")
            .description("Xiaomi 12 5G 256 GB Blauw")
            .ean("6934177763892")
            .manufacturerCode("37071")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product17);

        Product product18 = Product.builder()
            .name("Xiaomi Redmi Note 10 Pro")
            .description("Xiaomi Redmi Note 10 Pro 128 GB Onyx Gray")
            .ean("6934177734687")
            .manufacturerCode("31753")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product18);

        Product product19 = Product.builder()
            .name("Xiaomi Redmi Note 12")
            .description("Xiaomi Redmi Note 12 4G 128 GB Onyx Gray")
            .ean("6941812716922")
            .manufacturerCode("45813")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product19);

        Product product20 = Product.builder()
            .name("Xiaomi Redmi Note 11")
            .description("Xiaomi Redmi Note 11 128 GB Graphite Gray")
            .ean("6934177767333")
            .manufacturerCode("37651")
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        productRepository.save(product20);


        // Client Company
        ClientCompany clientCompany = ClientCompany.builder()
            .address(address)
            .vatNumber("BE0123456789")
            .name("Elision")
            .website("https://elision.eu")
            .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.CONSUMER_ELECTRONICS)))
            .build();

        clientCompanyRepository.save(clientCompany);


        // Tracked Products
        TrackedProduct trackedProduct1 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product1)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct1);

        TrackedProduct trackedProduct2 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product2)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct2);

        TrackedProduct trackedProduct3 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product3)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct3);

        TrackedProduct trackedProduct4 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product4)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct4);

        TrackedProduct trackedProduct5 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product5)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct5);

        TrackedProduct trackedProduct6 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product6)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct6);

        TrackedProduct trackedProduct7 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product7)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct7);

        TrackedProduct trackedProduct8 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product8)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct8);

        TrackedProduct trackedProduct9 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product9)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct9);

        TrackedProduct trackedProduct10 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product10)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct10);

        TrackedProduct trackedProduct11 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product11)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct11);

        TrackedProduct trackedProduct12 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product12)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct12);

        TrackedProduct trackedProduct13 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product13)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct13);

        TrackedProduct trackedProduct14 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product14)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct14);

        TrackedProduct trackedProduct15 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product15)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct15);

        TrackedProduct trackedProduct16 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product16)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct16);

        TrackedProduct trackedProduct17 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product17)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct17);

        TrackedProduct trackedProduct18 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product18)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct18);

        TrackedProduct trackedProduct19 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product19)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct19);

        TrackedProduct trackedProduct20 = TrackedProduct.builder()
            .productPurchaseCost(699.00)
            .productSellPrice(1099.00)
            .isTracked(true)
            .product(product20)
            .clientCompany(clientCompany)
            .build();

        trackedProductRepository.save(trackedProduct20);

        clientCompany.setTrackedProducts(new ArrayList<>(List.of(
            trackedProduct1,
            trackedProduct2,
            trackedProduct3,
            trackedProduct4,
            trackedProduct5,
            trackedProduct6,
            trackedProduct7,
            trackedProduct8,
            trackedProduct9,
            trackedProduct10,
            trackedProduct11,
            trackedProduct12,
            trackedProduct13,
            trackedProduct14,
            trackedProduct15,
            trackedProduct16,
            trackedProduct17,
            trackedProduct18,
            trackedProduct19,
            trackedProduct20
        )));

        user.setClientCompany(clientCompany);
        user = userRepository.save(user);
        alertSettingsRepository.save(alertSettings);
        user = userRepository.save(user);

        AlertRule alertRule = AlertRule.builder()
            .alertSettings(user.getAlertSettings())
            .product(product1)
            .price(1999.00)
            .priceComparisonType(PriceComparisonType.LOWER)
            .build();

        //alertRule = alertRuleRepository.save(alertRule);

        AlertRule alertRule2 = AlertRule.builder()
            .alertSettings(user.getAlertSettings())
            .product(product2)
            .price(500.01)
            .priceComparisonType(PriceComparisonType.HIGHER)
            .build();

        //alertRule2 = alertRuleRepository.save(alertRule2);

        alertSettings = user.getAlertSettings();
        alertSettings.setAlertRules(List.of(alertRule, alertRule2));
        alertSettings = alertSettingsRepository.save(alertSettings);

        this.alertRuleToDelete = alertSettings.getAlertRules().get(0);

        // Retailer Company
        RetailerCompany retailerCompany = RetailerCompany.builder()
            .name("Amazon")
            .website("https://amazon.com")
            .build();

        retailerCompanyRepository.save(retailerCompany);

        //Price
        Price price = Price.builder()
            .amount(699.00)
            .product(product1)
            .retailerCompany(retailerCompany)
            .timestamp(LocalDateTime.now().minusDays(1))
            .build();

        priceRepository.save(price);

        Price price2 = Price.builder()
            .amount(699.00)
            .product(product2)
            .retailerCompany(retailerCompany)
            .timestamp(LocalDateTime.now().minusDays(2))
            .build();

        priceRepository.save(price2);

        //Alerts
        Alert alert = Alert.builder()
            .price(price.getAmount())
            .priceComparisonType(PriceComparisonType.HIGHER)
            .retailerCompany(retailerCompany)
            .product(product1)
            .user(user)
            .timestamp(LocalDateTime.now().minusDays(1))
            .build();

        alertRepository.save(alert);

        Alert alert2 = Alert.builder()
            .price(price2.getAmount())
            .priceComparisonType(PriceComparisonType.LOWER)
            .retailerCompany(retailerCompany)
            .product(product2)
            .user(user)
            .timestamp(LocalDateTime.now().minusDays(2))
            .build();

        alertRepository.save(alert2);


        // Price Scraping Config
        PriceScrapingConfig priceScrapingConfig = PriceScrapingConfig.builder()
            .cssSelector("//span[@id='priceblock_ourprice']")
            .url(
                "https://www.amazon.com/Apple-iPhone-128GB-Graphite-Unlocked/dp/B08L5TNJHG/ref=sr_1_3?dchild=1&keywords=iphone+12+pro&qid=1621430008&sr=8-3")
            .active(true)
            .retailerCompany(retailerCompany)
            .product(product1)
            .commaSeparatedDecimal(true)
            .build();

        priceScrapingConfigRepository.save(priceScrapingConfig);


        assertNotEquals(0, retailerCompanyRepository.count());
        assertNotEquals(0, priceRepository.count());
        assertNotEquals(0, alertRepository.count());
        assertNotEquals(0, alertRuleRepository.count());
        assertNotEquals(0, priceScrapingConfigRepository.count());
        assertNotEquals(0, trackedProductRepository.count());
        assertNotEquals(0, productRepository.count());
        assertNotEquals(0, clientCompanyRepository.count());
        assertNotEquals(0, userRepository.count());
        assertNotEquals(0,
            alertRuleRepository.findAllByAlertSettings_Id(alertSettings.getId()).size());


    }

    @AfterEach
    void tearDown() {
        alertRepository.deleteAll();
        priceRepository.deleteAll();
        priceScrapingConfigRepository.deleteAll();
        trackedProductRepository.deleteAll();
        alertRuleRepository.deleteAll();
        productRepository.deleteAll();
        retailerCompanyRepository.deleteAll();
        clientCompanyRepository.deleteAll();
        userRepository.deleteAll();

        assertEquals(0, (int) (retailerCompanyRepository.count() + priceRepository.count()
            + alertRepository.count() + priceScrapingConfigRepository.count()
            + trackedProductRepository.count() + productRepository.count()
            + clientCompanyRepository.count() + userRepository.count()));
    }

    @Test
    void getAlertRulesForAuthenticatedUserCorrectly() throws Exception {

        User user = userRepository.findAll().get(0);

        mockMvc.perform(get("/api/alert-rules")
                .with(user(user))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[*].priceComparisonType").value(hasItem("LOWER")))
            .andExpect(jsonPath("$[*].priceComparisonType").value(hasItem("HIGHER")))
            .andExpect(jsonPath("$[*].priceThreshold").value(hasItem(500.01)))
            .andExpect(jsonPath("$[*].priceThreshold").value(hasItem(1999.00)));


    }

    @Test
    void createAlertRuleCorrectly() throws Exception {

        User user = userRepository.findAll().get(0);
        Product product = productRepository.findAll().get(0);
        AlertRuleToCreateDto alertRuleDto = AlertRuleToCreateDto.builder()
            .priceComparisonType(PriceComparisonType.LOWER)
            .priceThreshold(123.00)
            .product(ProductDto.builder().id(product.getId()).build())
            .retailerCompanies(List.of())
            .build();

        Gson gson = new Gson();
        String json = gson.toJson(alertRuleDto);
        log.debug(">>>>>>>json alert rule:  " + json);


        assertNotNull(productRepository.findById(product.getId()).orElse(null));
        mockMvc.perform(post("/api/alert-rules")
                .with(user(user))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.priceComparisonType").value("LOWER"))
            .andExpect(jsonPath("$.priceThreshold").value(123.00))
            .andExpect(jsonPath("$.product.id").value(product.getId().toString()))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.id").value(hasLength(36)));

        assertEquals(3, alertRuleRepository.count());
        assertEquals(3,
            alertRuleRepository.findAllByAlertSettings_Id(user.getAlertSettings().getId()).size());


    }

    //TODO: correct this test
    /*@Test
    void deleteAlertRuleCorrectly() throws Exception {

        User user = userRepository.findAll().get(0);
        long alertRuleCount = alertRuleRepository.count();

        List<UUID> ids = List.of(this.alertRuleToDelete.getId());
        Gson gson = new Gson();
        String json = gson.toJson(ids);

        log.debug(">>>>>>>json alert rules to delete:  " + json);


        mockMvc.perform(delete("/api/alert-rules/")
                .with(user(user))
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNoContent());

        assertEquals(alertRuleCount - 1, alertRuleRepository.count());
        assertEquals(1,
            alertRuleRepository.findAllByAlertSettings_Id(user.getAlertSettings().getId()).size());

    }*/

}