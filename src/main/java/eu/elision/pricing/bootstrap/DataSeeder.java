package eu.elision.pricing.bootstrap;

import eu.elision.pricing.domain.*;
import eu.elision.pricing.repository.*;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class is used to seed the database with some initial data.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("seed-data")
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientCompanyRepository clientCompanyRepository;
    private final PriceScrapingConfigRepository priceScrapingConfigRepository;
    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final RetailerCompanyRepository retailerCompanyRepository;
    private final TrackedProductRepository trackedProductRepository;
    private final AlertRepository alertRepository;
    private final AlertSettingsRepository alertSettingsRepository;

    @Override
    public void run(String... args) throws Exception {

        AlertSettings alertSettings = AlertSettings.builder()
                .notifyViaEmail(true)
                .alertStorageDuration(30)
                .build();

        // Users
        User user = User.builder()
                .email("mikadom@cronos.be")
                .password(passwordEncoder.encode("secure_password"))
                .firstName("Dominik")
                .lastName("Mika")
                .role(Role.ADMIN)
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
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product1);

        Product product2 = Product.builder()
                .name("Apple iPhone 14 Pro")
                .description("128GB, Spacezwart")
                .ean("194253401179")
                .manufacturerCode("MPXV3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product2);

        Product product3 = Product.builder()
                .name("Apple iPhone 14")
                .description("128GB, Middernacht")
                .ean("194253408253")
                .manufacturerCode("MPUF3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product3);

        Product product4 = Product.builder()
                .name("Apple iPhone 13")
                .description("128GB, Black")
                .ean("194252707289")
                .manufacturerCode("MLPF3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product4);

        Product product5 = Product.builder()
                .name("Apple iPhone 12")
                .description("128GB, Black")
                .ean("194252031315")
                .manufacturerCode("MGJA3ZD/A")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product5);

        Product product6 = Product.builder()
                .name("Samsung Galaxy S23 Ultra 5G")
                .description("512GB, Phantom Black")
                .ean("8806094729207")
                .manufacturerCode("SM-S918BZKHEUB")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product6);

        Product product7 = Product.builder()
                .name("Samsung Galaxy S22 Ultra 5G")
                .description("128GB, Phantom Black")
                .ean("8806092879140")
                .manufacturerCode("SM-S908BZKDEUB")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product7);

        Product product8 = Product.builder()
                .name("Samsung Galaxy Z Fold4")
                .description("256GB, Phantom Black")
                .ean("8806094504682")
                .manufacturerCode("SM-F936BZKBEUB")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product8);

        Product product9 = Product.builder()
                .name("Samsung Galaxy A54 5G")
                .description("Samsung Galaxy A54 5G 128 GB Awesome Graphite")
                .ean("8806094885699")
                .manufacturerCode("SM-A546BZKCEUB")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product9);

        Product product10 = Product.builder()
                .name("Samsung Galaxy A34 5G")
                .description("Samsung Galaxy A34 5G 128 GB Awesome Graphite")
                .ean("8806094813845")
                .manufacturerCode("SM-A346BZKAEUB")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product10);

        Product product11 = Product.builder()
                .name("Samsung Galaxy A23 5G")
                .description("Samsung Galaxy A23 5G 128 GB Awesome Black")
                .ean("8806094534047")
                .manufacturerCode("SM-A236BZKVEUB")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product11);

        Product product12 = Product.builder()
                .name("OnePlus Nord 2T")
                .description("OnePlus Nord 2T Grey Shadow 128GB")
                .ean("6921815621331")
                .manufacturerCode("5011102071")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product12);

        Product product13 = Product.builder()
                .name("OnePlus Nord CE 2 Lite")
                .description("OnePlus Nord CE 2 Lite Black 128GB 6GB 5G")
                .ean("6921815620716")
                .manufacturerCode("5011102002")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product13);

        Product product14 = Product.builder()
                .name("OnePlus 9")
                .description("OnePlus 9 Astral Black 128GB")
                .ean("6921815615378")
                .manufacturerCode("5011101552")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product14);

        Product product15 = Product.builder()
                .name("OnePlus Nord")
                .description("OnePlus Nord Blue 128GB")
                .ean("6921815611752")
                .manufacturerCode("5011101199")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product15);

        Product product16 = Product.builder()
                .name("Xiaomi Redmi Note 11 Pro")
                .description("Xiaomi Redmi Note 11 Pro 128 GB Graphite Gray")
                .ean("6934177769733")
                .manufacturerCode("37969")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product16);

        Product product17 = Product.builder()
                .name("Xiaomi 12 5G")
                .description("Xiaomi 12 5G 256 GB Blauw")
                .ean("6934177763892")
                .manufacturerCode("37071")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product17);

        Product product18 = Product.builder()
                .name("Xiaomi Redmi Note 10 Pro")
                .description("Xiaomi Redmi Note 10 Pro 128 GB Onyx Gray")
                .ean("6934177734687")
                .manufacturerCode("31753")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product18);

        Product product19 = Product.builder()
                .name("Xiaomi Redmi Note 12")
                .description("Xiaomi Redmi Note 12 4G 128 GB Onyx Gray")
                .ean("6941812716922")
                .manufacturerCode("45813")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product19);

        Product product20 = Product.builder()
                .name("Xiaomi Redmi Note 11")
                .description("Xiaomi Redmi Note 11 128 GB Graphite Gray")
                .ean("6934177767333")
                .manufacturerCode("37651")
                .category(ProductCategory.ELECTRONICS)
                .build();

        productRepository.save(product20);


        // Client Company
        ClientCompany clientCompany = ClientCompany.builder()
                .address(address)
                .VATNumber("BE0123456789")
                .name("Elision")
                .website("https://elision.eu")
                .categoriesProductsSold(new HashSet<>(List.of(ProductCategory.ELECTRONICS)))
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
        userRepository.save(user);


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
            .price(price)
            .priceComparisonType(PriceComparisonType.HIGHER)
            .retailerCompany(retailerCompany)
            .product(product1)
            .user(user)
            .timestamp(LocalDateTime.now().minusDays(1))
            .build();

        alertRepository.save(alert);

        Alert alert2 = Alert.builder()
            .price(price2)
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
    }
}
