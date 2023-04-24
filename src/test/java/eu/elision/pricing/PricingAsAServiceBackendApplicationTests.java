package eu.elision.pricing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PricingAsAServiceBackendApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(()-> SpringApplication.run(PricingAsAServiceBackendApplication.class));
    }

}
