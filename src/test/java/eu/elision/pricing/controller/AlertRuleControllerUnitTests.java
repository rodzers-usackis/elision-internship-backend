package eu.elision.pricing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertRuleDto;
import eu.elision.pricing.repository.UserRepository;
import eu.elision.pricing.service.AlertRuleServiceImpl;
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

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class AlertRuleControllerUnitTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private AlertRuleServiceImpl alertRuleService;

    //@InjectMocks
    //private AlertRuleController alertRuleController;


    @BeforeEach
    void setUp() {

        userRepository.save(new User("FirstName", "LastName", "test@test.be",
            "password"));
    }


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void return204WhenDeletedCorrectly() throws Exception {

        User user = userRepository.findAll().get(0);

        //don't return anything
        doNothing().when(alertRuleService).deleteAllByIdIn(eq(user), any());

        log.debug(">>> request");

        mockMvc.perform(delete("/api/alert-rules")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(user))
                .content("[\"" + UUID.randomUUID().toString() + "\"]"))
            .andExpect(status().isNoContent());


    }

    @Test
    void return200WhenPatchedCorrectly() throws Exception {

        User user = userRepository.findAll().get(0);

        Gson gson = new Gson();

        AlertRuleDto alertRuleDto = AlertRuleDto.builder()
            .id(UUID.randomUUID())
            .priceThreshold(400)
            .priceComparisonType(PriceComparisonType.LOWER)
            .build();

        doNothing().when(alertRuleService).updateAlertRule(eq(user), any());

        mockMvc.perform(patch("/api/alert-rules")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(user))
                .content(gson.toJson(alertRuleDto)))
            .andExpect(status().isOk());



    }

}