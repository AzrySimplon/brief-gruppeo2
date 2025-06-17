package fr.simplon.gruppeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.simplon.gruppeo.model.User;
import fr.simplon.gruppeo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void registerUser_Success() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testUser.getUsername()))
                .andExpect(jsonPath("$.password").exists());
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void registerUser_DuplicateUsername() throws Exception {
        // First registration
        userRepository.save(testUser);

        // Attempt to register with same username
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username is already taken!"));
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void login_Success() throws Exception {
        // First register the user and capture the response
        MvcResult registrationResult = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        // Create login credentials
        User loginUser = new User();
        loginUser.setUsername(testUser.getUsername());
        loginUser.setPassword(testUser.getPassword()); // Use original non-encoded password

        // Attempt login
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.tupe").value("Bearer"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString())); // For debugging
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void login_InvalidCredentials() throws Exception {
        User invalidUser = new User();
        invalidUser.setUsername("wrongUser");
        invalidUser.setPassword("wrongPassword");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid username or password!"));
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void login_NonExistentUser() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid username or password!"));
    }
}