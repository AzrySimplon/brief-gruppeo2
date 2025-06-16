package fr.simplon.gruppeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.simplon.gruppeo.model.Person;
import fr.simplon.gruppeo.model.User;
import fr.simplon.gruppeo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Create test users
        testUser1 = new User("testUser1", "password1");
        testUser2 = new User("testUser2", "password2");
    }

    @Test
    void createUser_ShouldCreateNewUser() throws Exception {
        User newUser = new User("newUser", "newPassword");
        
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("newUser")))
                .andExpect(jsonPath("$.password", is("newPassword")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        testUser1 = userRepository.save(testUser1);
        testUser2 = userRepository.save(testUser2);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(testUser1.getUsername())))
                .andExpect(jsonPath("$[1].username", is(testUser2.getUsername())));
    }

    @Test
    void getUserById_WithValidId_ShouldReturnUser() throws Exception {
        testUser1 = userRepository.save(testUser1);

        mockMvc.perform(get("/user/{id}", testUser1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(testUser1.getUsername())))
                .andExpect(jsonPath("$.password", is(testUser1.getPassword())));
    }

    @Test
    void getUserById_WithInvalidId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/user/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_WithValidId_ShouldUpdateUser() throws Exception {
        User updatedUser = new User("updatedUsername", "updatedPassword");
        testUser1 = userRepository.save(testUser1);
        
        mockMvc.perform(put("/user/{id}", testUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updatedUsername")))
                .andExpect(jsonPath("$.password", is("updatedPassword")));
    }

    @Test
    void updateUser_WithInvalidId_ShouldReturn404() throws Exception {
        User updatedUser = new User("updatedUsername", "updatedPassword");
        
        mockMvc.perform(put("/user/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_WithValidId_ShouldDeleteUser() throws Exception {
        testUser1 = userRepository.save(testUser1);

        mockMvc.perform(delete("/user/{id}", testUser1.getId()))
                .andExpect(status().isOk());

        // Verify user is deleted
        mockMvc.perform(get("/user/{id}", testUser1.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_WithInvalidId_ShouldReturn404() throws Exception {
        mockMvc.perform(delete("/user/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByUsername_WithValidUsername_ShouldReturnUser() throws Exception {
        testUser1 = userRepository.save(testUser1);

        mockMvc.perform(get("/user/username/{username}", testUser1.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(testUser1.getUsername())))
                .andExpect(jsonPath("$.password", is(testUser1.getPassword())));
    }

    @Test
    void getUserByUsername_WithInvalidUsername_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/user/username/{username}", "nonexistentUser"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_WithPerson_ShouldUpdateUserWithPerson() throws Exception {
        testUser1 = userRepository.save(testUser1);

        Person person = new Person();
        person.setId(1L);
        
        User userWithPerson = new User("userWithPerson", "password");
        userWithPerson.setPerson(person);
        
        mockMvc.perform(put("/user/{id}", testUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWithPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("userWithPerson")))
                .andExpect(jsonPath("$.person.id", is(1)));
    }
}