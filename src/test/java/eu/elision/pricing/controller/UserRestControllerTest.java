package eu.elision.pricing.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.UserRepository;
import eu.elision.pricing.service.JwtService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
class UserRestControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;


    @BeforeAll
    void setup() {

        User user = User.builder()
            .firstName("Test")
            .lastName("User")
            .password(passwordEncoder.encode("password"))
            .email("test2222@test.be")
            .role(Role.CLIENT)
            .build();

        userRepository.save(user);

    }

    @Test
    void getAuthenticatedUsersInformation() throws Exception {

        User user = userRepository.findAll().get(0);

        mockMvc.perform(get("/api/users/me")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()))
            .andExpect(jsonPath("$.role").value(user.getRole().toString()));



    }

    @Test
    void gettingUserInfoAsUnauthenticatedReturns403() throws Exception {

        mockMvc.perform(get("/api/users/me")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());


    }

    @Test
    void userGetsTheirInfoWhenTheyMakeRequestWithJwt() throws Exception {
        User user = userRepository.findAll().get(0);

        String jwt = jwtService.generateToken(user);


        mockMvc.perform(get("/api/users/me")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwt))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()))
            .andExpect(jsonPath("$.role").value(user.getRole().toString()));



    }

    @AfterAll
    void cleanUp() {
        userRepository.deleteAll();
    }


}