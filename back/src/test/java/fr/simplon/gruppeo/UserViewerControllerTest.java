package fr.simplon.gruppeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.simplon.gruppeo.model.Person;
import fr.simplon.gruppeo.model.Gender;
import fr.simplon.gruppeo.model.Profile;
import fr.simplon.gruppeo.model.UserViewer;
import fr.simplon.gruppeo.repository.PersonRepository;
import fr.simplon.gruppeo.repository.UserViewerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserViewerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserViewerRepository userViewerRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserViewer testViewer1;
    private UserViewer testViewer2;
    private Person testPerson;

    @BeforeEach
    void setUp() {
        userViewerRepository.deleteAll();
        personRepository.deleteAll();

        // Create test person
        testPerson = new Person(
                "Test Person",
                Gender.X,
                3,
                false,
                2,
                Profile.SHY,
                LocalDate.of(1990, 1, 1)
        );

        // Create test viewers
        testViewer1 = new UserViewer("testViewer1", null);
        testViewer2 = new UserViewer("testViewer2", null);
    }

    // Test user viewer creation
    @Test
    void shouldCreateUserViewer() throws Exception {
        mockMvc.perform(post("/user-viewer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testViewer1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testViewer1.getUsername()));
    }

    // Test user viewer creation with person
    @Test
    void shouldCreateUserViewerWithPerson() throws Exception {
        Person savedPerson = personRepository.save(testPerson);
        testViewer1.setPerson(savedPerson);

        mockMvc.perform(post("/user-viewer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testViewer1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testViewer1.getUsername()))
                .andExpect(jsonPath("$.person.name").value(testPerson.getName()));
    }

    // Test user viewer creation with person and username
    @Test
    void shouldGetAllUserViewers() throws Exception {
        userViewerRepository.save(testViewer1);
        userViewerRepository.save(testViewer2);

        mockMvc.perform(get("/user-viewer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value(testViewer1.getUsername()))
                .andExpect(jsonPath("$[1].username").value(testViewer2.getUsername()));
    }

    // Test getting a user viewer by id
    @Test
    void shouldGetUserViewerById() throws Exception {
        UserViewer savedViewer = userViewerRepository.save(testViewer1);

        mockMvc.perform(get("/user-viewer/{id}", savedViewer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testViewer1.getUsername()));
    }

    // Test getting a user viewer by id that does not exist
    @Test
    void shouldReturn404WhenGettingNonExistentUserViewer() throws Exception {
        mockMvc.perform(get("/user-viewer/999"))
                .andExpect(status().isNotFound());
    }

    // Test updating a user viewer
    @Test
    void shouldUpdateUserViewer() throws Exception {
        UserViewer savedViewer = userViewerRepository.save(testViewer1);
        testViewer1.setUsername("updatedUsername");

        mockMvc.perform(put("/user-viewer/{id}", savedViewer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testViewer1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testViewer1.getUsername()));
    }

    // Test updating a user viewer that does not exist
    @Test
    void shouldUpdateUserViewerWithPerson() throws Exception {
        UserViewer savedViewer = userViewerRepository.save(testViewer1);
        Person savedPerson = personRepository.save(testPerson);
        testViewer1.setPerson(savedPerson);

        mockMvc.perform(put("/user-viewer/{id}", savedViewer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testViewer1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testViewer1.getUsername()))
                .andExpect(jsonPath("$.person.name").value(testPerson.getName()));
    }

    // Test updating a user viewer that does not exist
    @Test
    void shouldReturn404WhenUpdatingNonExistentUserViewer() throws Exception {
        mockMvc.perform(put("/user-viewer/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testViewer1)))
                .andExpect(status().isNotFound());
    }

    // Test deleting a user viewer
    @Test
    void shouldDeleteUserViewer() throws Exception {
        UserViewer savedViewer = userViewerRepository.save(testViewer1);

        mockMvc.perform(delete("/user-viewer/{id}", savedViewer.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user-viewer/{id}", savedViewer.getId()))
                .andExpect(status().isNotFound());
    }

    // Test deleting a user viewer that does not exist
    @Test
    void shouldReturn404WhenDeletingNonExistentUserViewer() throws Exception {
        mockMvc.perform(delete("/user-viewer/999"))
                .andExpect(status().isNotFound());
    }

    // Test finding a user viewer by username
    @Test
    void shouldFindUserViewerByUsername() throws Exception {
        UserViewer savedViewer = userViewerRepository.save(testViewer1);

        mockMvc.perform(get("/user-viewer/username/{username}", testViewer1.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedViewer.getId()))
                .andExpect(jsonPath("$.username").value(testViewer1.getUsername()));
    }

    // Test finding a user viewer by username that does not exist
    @Test
    void shouldReturn404WhenFindingNonExistentUsername() throws Exception {
        mockMvc.perform(get("/user-viewer/username/nonexistent"))
                .andExpect(status().isNotFound());
    }
}