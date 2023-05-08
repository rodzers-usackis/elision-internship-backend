package eu.elision.pricing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest
class PricingAsaServiceBackendApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> SpringApplication.run(PricingAsaServiceBackendApplication.class));
    }

}
