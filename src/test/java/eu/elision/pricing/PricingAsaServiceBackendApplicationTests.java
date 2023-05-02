package eu.elision.pricing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PricingAsaServiceBackendApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> SpringApplication.run(PricingAsaServiceBackendApplication.class));
    }

}
