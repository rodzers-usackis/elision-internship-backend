package eu.elision.pricing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.ScrapingTaskDto;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import eu.elision.pricing.repository.UserRepository;
import eu.elision.pricing.service.HttpRequestService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class ScrapingTaskControllerTests {

    //@MockBean
    //private PriceService priceService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private PriceScrapingConfigRepository priceScrapingConfigRepository;

    @MockBean
    private HttpRequestService httpRequestService;

    private final Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        userRepository.save(User.builder()
            .firstName("test")
            .lastName("test")
            .email("email@test.be")
            .role(Role.CLIENT)
            .password("test")
            .build());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    void testTriggerScrapingTaskAsynchronously() throws Exception {

        User user = userRepository.findAll().get(0);

        ScrapingTaskDto scrapingTaskDto = ScrapingTaskDto.builder()
            .productIds(List.of(UUID.randomUUID()))
            .build();

        String json = gson.toJson(scrapingTaskDto);

        doAnswer(invocation -> {
            log.debug(">>> requesting some url");
            log.debug(">>> wait 10s");
            Thread.sleep(10000);
            log.debug(">>> done waiting");
            return null;
        }).when(httpRequestService).getHtml(any());

        Product product = Product.builder().id(UUID.randomUUID()).build();
        PriceScrapingConfig priceScrapingConfig = PriceScrapingConfig.builder()
            .url("test url")
            .product(product)
            .build();

        when(priceScrapingConfigRepository.findAllByActiveTrueAndProduct_IdIn(anyList()))
            .thenReturn(List.of(priceScrapingConfig));


        mockMvc.perform(post("/api/scraping-tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(user(user))
            )
            .andExpect(status().isOk());



    }


}