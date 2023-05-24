package eu.elision.pricing.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.elision.pricing.domain.Address;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.RegistrationRequest;
import eu.elision.pricing.repository.UserRepository;
import eu.elision.pricing.service.AuthenticationService;
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

import java.util.List;
import java.util.Set;

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
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    RegistrationRequest existingUser = RegistrationRequest.builder()
            .companyName("TestCompany")
            .vatNumber("BE1234562789")
            .companyWebsite("www.testcompany.be")
            .productCategory(Set.of(ProductCategory.CONSUMER_ELECTRONICS))
            .streetAddress("TestStreet 1")
            .streetNumber("1")
            .city("TestCity")
            .zipCode("1234")
            .country("TestCountry")
            .firstName("FirstName")
            .lastName("LastName")
            .emailAddress("existingemail@email.com")
            .password("secure_password")
            .build();


    @BeforeAll
    void setup() {
        authenticationService.register(existingUser);

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

        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .companyName("TestCompany")
                .vatNumber("BE1123456789")
                .companyWebsite("www.testcompany.be")
                .productCategory(Set.of(ProductCategory.CONSUMER_ELECTRONICS))
                .streetAddress("TestStreet 1")
                .streetNumber("1")
                .city("TestCity")
                .zipCode("1234")
                .country("TestCountry")
                .firstName("FirstName")
                .lastName("LastName")
                .emailAddress("newemail@email.com")
                .password("secure_password")
                .build();


        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated());

        assertTrue(userRepository.findByEmail(registrationRequest.getEmailAddress()).isPresent());

        //cleanup
        userRepository.delete(userRepository.findByEmail(registrationRequest.getEmailAddress()).get());
    }

    @Test
    void cantRegisterWithAlreadyRegisteredEmail() throws Exception {

        assertTrue(userRepository.findByEmail(existingUser.getEmailAddress()).isPresent());

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                .andExpect(status().isConflict());


    }

    @AfterAll
    void cleanUp() {
        userRepository.deleteAll();
    }
}