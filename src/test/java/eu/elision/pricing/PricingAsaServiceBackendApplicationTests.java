package eu.elision.pricing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
class PricingAsaServiceBackendApplicationTests {

    @Test
    void contextLoads() {
        //Test disabled because it uses the real database, which will not be available in the pipeline
//        assertDoesNotThrow(() -> SpringApplication.run(PricingAsaServiceBackendApplication.class));
    }

}
