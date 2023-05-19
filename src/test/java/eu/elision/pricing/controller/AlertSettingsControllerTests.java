package eu.elision.pricing.controller;

import eu.elision.pricing.domain.*;
import eu.elision.pricing.repository.UserRepository;
import eu.elision.pricing.service.AlertSettingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AlertSettingsControllerTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlertSettingsService alertSettingsService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenAlertSettingsWhenGetAlertSettingsThenReturnAlertSettings() throws Exception {
//        ClientCompany clientCompany = ClientCompany.builder()
//                .id(UUID.randomUUID())
//                .name("Test Company")
//                .build();
//
//// Create the AlertSettings
//        AlertSettings alertSettings = AlertSettings.builder()
//                .emailNotifications(true)
//                .alertStorageDuration(AlertStorageDuration.ONE_MONTH)
//                .build();
//
//// Create the User
//        User user = User.builder()
//                .id(UUID.randomUUID())
//                .email("test@elision.eu")
//                .password("secure_password")
//                .firstName("John")
//                .lastName("Smith")
//                .role(Role.ADMIN)
//                .build();
//
//// Set the alertSettings in the User entity
//        user.setAlertSettings(alertSettings);
//
//// Set the user in the AlertSettings entity
//        alertSettings.setUser(user);
//        alertSettings.setEmailAddress(user.getEmail());
//
//// Set the clientCompany in the User entity
//        user.setClientCompany(clientCompany);
//
//// Perform your test or assertions
//// ...
//
//
//        mockMvc.perform(get("/api/alert-settings")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(user(user)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isEmailNotifications").value(alertSettings.isEmailNotifications()))
//                .andExpect(jsonPath("$.emailAddress").value(alertSettings.getEmailAddress()))
//                .andExpect(jsonPath("$.alertStorageDuration").value(alertSettings.getAlertStorageDuration().toString()));
//    }
}
