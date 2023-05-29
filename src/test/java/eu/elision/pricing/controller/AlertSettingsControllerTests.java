package eu.elision.pricing.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import eu.elision.pricing.domain.*;
import eu.elision.pricing.repository.UserRepository;
import eu.elision.pricing.service.AlertSettingsService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class AlertSettingsControllerTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlertSettingsService alertSettingsService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenAlertSettingsWhenGetAlertSettingsThenReturnAlertSettings() throws Exception {

        // Create the AlertSettings
        AlertSettings alertSettings = AlertSettings.builder()
            .notifyViaEmail(true)
            .alertsActive(false)
            .alertStorageDuration(30)
            .build();


        // Create the User
        User user = User.builder()
            .id(UUID.randomUUID())
            .email("test@elision.eu")
            .password("secure_password")
            .firstName("John")
            .lastName("Smith")
            .role(Role.ADMIN)
            .build();

        final ClientCompany clientCompany = ClientCompany.builder()
            .id(UUID.randomUUID())
            .name("Test Company")
            .build();

        user.setAlertSettings(alertSettings);
        alertSettings.setUser(user);
        alertSettings.setEmailAddress(user.getEmail());
        user.setClientCompany(clientCompany);


        mockMvc.perform(get("/api/alert-settings")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.notifyViaEmail").value(alertSettings.isNotifyViaEmail()))
            .andExpect(jsonPath("$.alertsActive").value(alertSettings.isAlertsActive()))
            .andExpect(jsonPath("$.emailAddress").value(alertSettings.getEmailAddress()))
            .andExpect(jsonPath("$.alertStorageDuration").value(
                alertSettings.getAlertStorageDuration()));
    }
}
