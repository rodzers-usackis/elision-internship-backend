package eu.elision.pricing.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.UserRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerIntegrationTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeAll
    void setup() {

        userRepository.save(new User("FirstName", "LastName", "test@test.be",
            passwordEncoder.encode("secure_password")));

    }


    @Test
    void successfulLogin() throws Exception {

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                                        "email": "%s",
                                        "password": "%s"
                    }
                                    """.formatted("test@test.be", "secure_password")))
            .andExpect(status().isOk());

    }

    @Test
    void cantLoginWithUnregisteredEmail() throws Exception {

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                                        "email": "%s",
                                        "password": "%s"
                    }
                                    """.formatted("not.an.existing.account@test.be",
                    "secure_password")))
            .andExpect(status().is4xxClientError());

    }

    @Test
    void cantLoginWithWrongPassword() throws Exception {

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                                        "email": "%s",
                                        "password": "%s"
                    }
                                    """.formatted("test@test.be", "secure_password1")))
            .andExpect(status().is4xxClientError());

    }

    @Test
    void successfulRegistration() throws Exception {

        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "test_email@register.be";
        String password = "qwerty123";

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                                        "firstName": "%s",
                                        "lastName": "%s",
                                        "email": "%s",
                                        "password": "%s"
                    }
                                    """.formatted(firstName, lastName, email, password)))
            .andExpect(status().isCreated());

        assertTrue(userRepository.findByEmail(email).isPresent());

        //cleanup
        userRepository.delete(userRepository.findByEmail(email).get());

    }

    @Test
    void cantRegisterWithAlreadyRegisteredEmail() throws Exception {

        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "test@test.be";
        String password = "qwerty123";

        assertTrue(userRepository.findByEmail(email).isPresent());

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                                        "firstName": "%s",
                                        "lastName": "%s",
                                        "email": "%s",
                                        "password": "%s"
                    }
                                    """.formatted(firstName, lastName, email, password)))
            .andExpect(status().isConflict());


    }

    @AfterAll
    void cleanUp() {
        userRepository.deleteAll();
    }
}