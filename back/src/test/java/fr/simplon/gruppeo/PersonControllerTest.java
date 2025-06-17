package fr.simplon.gruppeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.simplon.gruppeo.model.Gender;
import fr.simplon.gruppeo.model.Profile;
import fr.simplon.gruppeo.repository.PersonRepository;
import fr.simplon.gruppeo.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test") //tests are done with h2 Driver
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Person testPerson;
    private Person testPerson2;

    @BeforeEach
    void setUp() {
        testPerson = new Person("Test1", Gender.X, 5, true, 2, Profile.EXTROVERT, LocalDate.now());
        testPerson2 = new Person("Test2", Gender.F, 2, false, 6, Profile.SHY, LocalDate.now());

        personRepository.deleteAll();
    }

    //Test person creation
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldCreatePerson() throws Exception {
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testPerson.getName()))
                .andExpect(jsonPath("$.gender").value(testPerson.getGender().toString()))
                .andExpect(jsonPath("$.french_knowledge").value(testPerson.getFrench_knowledge()))
                .andExpect(jsonPath("$.old_DWWM").value(testPerson.getOld_DWWM()))
                .andExpect(jsonPath("$.technical_knowledge").value(testPerson.getTechnical_knowledge()))
                .andExpect(jsonPath("$.profile").value(testPerson.getProfile().toString()));
    }

    //Test getting all persons
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldGetAllPersons() throws Exception {
        Person savedPerson1 = personRepository.save(testPerson);
        Person savedPerson2 = personRepository.save(testPerson2);

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(savedPerson1.getName(), savedPerson2.getName())))
                .andExpect(jsonPath("$[*].gender", containsInAnyOrder(savedPerson1.getGender().toString(), savedPerson2.getGender().toString())));
    }

    //Test getting a person by id
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldGetPersonById() throws Exception {
        Person savedPerson = personRepository.save(testPerson);

        mockMvc.perform(get("/person/{id}", savedPerson.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testPerson.getName()))
                .andExpect(jsonPath("$.gender").value(testPerson.getGender().toString()));
    }

    //Test getting a person by id that does not exist
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldReturn404WhenGetNonExistingPerson() throws Exception {
        mockMvc.perform(get("/person/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    //Test updating a person
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldUpdatePerson() throws Exception {
        Person savedPerson = personRepository.save(testPerson);
        testPerson.setName("UpdatedName");
        testPerson.setGender(Gender.M);

        mockMvc.perform(put("/person/{id}", savedPerson.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testPerson.getName()))
                .andExpect(jsonPath("$.gender").value(testPerson.getGender().toString()));
    }

    //Test updating a person that does not exist
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldReturn404WhenUpdatingNonExistingPerson() throws Exception {
        mockMvc.perform(put("/person/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPerson)))
                .andExpect(status().isNotFound());
    }

    //Test deleting a person
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldDeletePerson() throws Exception {
        Person savedPerson = personRepository.save(testPerson);

        mockMvc.perform(delete("/person/{id}", savedPerson.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/person/{id}", savedPerson.getId()))
                .andExpect(status().isNotFound());
    }

    //Test deleting a person that does not exist
    @Test
    @WithMockUser(username = "testUser", password = "pass")
    void shouldReturn404WhenDeletingNonExistingPerson() throws Exception {
        mockMvc.perform(delete("/person/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}