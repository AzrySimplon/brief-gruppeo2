package fr.simplon.gruppeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.simplon.gruppeo.model.PersonList;
import fr.simplon.gruppeo.model.UserAdmin;
import fr.simplon.gruppeo.repository.PersonListRepository;
import fr.simplon.gruppeo.repository.UserAdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserAdminRepository userAdminRepository;

    @Autowired
    private PersonListRepository personListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserAdmin testAdmin1;
    private UserAdmin testAdmin2;
    private PersonList testList;

    @BeforeEach
    void setUp() {
        userAdminRepository.deleteAll();
        personListRepository.deleteAll();

        testAdmin1 = new UserAdmin("testAdmin1");
        testAdmin2 = new UserAdmin("testAdmin2");
        testList = new PersonList("TestList", 0);
    }

    // Test user admin creation
    @Test
    void shouldCreateUserAdmin() throws Exception {
        mockMvc.perform(post("/user-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAdmin1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testAdmin1.getUsername()));
    }

    // Test getting all user admins
    @Test
    void shouldGetAllUserAdmins() throws Exception {
        userAdminRepository.save(testAdmin1);
        userAdminRepository.save(testAdmin2);

        mockMvc.perform(get("/user-admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value(testAdmin1.getUsername()))
                .andExpect(jsonPath("$[1].username").value(testAdmin2.getUsername()));
    }

    // Test getting a user admin by id
    @Test
    void shouldGetUserAdminById() throws Exception {
        UserAdmin savedAdmin = userAdminRepository.save(testAdmin1);

        mockMvc.perform(get("/user-admin/{id}", savedAdmin.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testAdmin1.getUsername()));
    }

    // Test getting a user admin by id that does not exist
    @Test
    void shouldReturn404WhenGettingNonExistentUserAdmin() throws Exception {
        mockMvc.perform(get("/user-admin/999"))
                .andExpect(status().isNotFound());
    }

    // Test updating a user admin
    @Test
    void shouldUpdateUserAdmin() throws Exception {
        UserAdmin savedAdmin = userAdminRepository.save(testAdmin1);
        testAdmin1.setUsername("updatedUsername");

        mockMvc.perform(put("/user-admin/{id}", savedAdmin.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAdmin1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testAdmin1.getUsername()));
    }

    // Test updating a user admin that does not exist
    @Test
    void shouldReturn404WhenUpdatingNonExistentUserAdmin() throws Exception {
        mockMvc.perform(put("/user-admin/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAdmin1)))
                .andExpect(status().isNotFound());
    }

    // Test deleting a user admin
    @Test
    void shouldDeleteUserAdmin() throws Exception {
        UserAdmin savedAdmin = userAdminRepository.save(testAdmin1);

        mockMvc.perform(delete("/user-admin/{id}", savedAdmin.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user-admin/{id}", savedAdmin.getId()))
                .andExpect(status().isNotFound());
    }

    // Test deleting a user admin that does not exist
    @Test
    void shouldReturn404WhenDeletingNonExistentUserAdmin() throws Exception {
        mockMvc.perform(delete("/user-admin/999"))
                .andExpect(status().isNotFound());
    }

    // Test adding a person list to an admin
    @Test
    void shouldAddPersonListToAdmin() throws Exception {
        UserAdmin savedAdmin = userAdminRepository.save(testAdmin1);
        PersonList savedList = personListRepository.save(testList);

        mockMvc.perform(post("/user-admin/{adminId}/person-lists/{listId}", 
                savedAdmin.getId(), savedList.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personLists", hasSize(1)))
                .andExpect(jsonPath("$.personLists[0].name").value(testList.getName()));
    }

    // Test adding a person list to an admin that does not exist
    @Test
    void shouldRemovePersonListFromAdmin() throws Exception {
        UserAdmin savedAdmin = userAdminRepository.save(testAdmin1);
        PersonList savedList = personListRepository.save(testList);
        savedAdmin.addPersonList(savedList);
        userAdminRepository.save(savedAdmin);

        mockMvc.perform(delete("/user-admin/{adminId}/person-lists/{listId}", 
                savedAdmin.getId(), savedList.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personLists", hasSize(0)));
    }

    // Test adding a person list to an admin that does not exist
    @Test
    void shouldGetAdminPersonLists() throws Exception {
        UserAdmin savedAdmin = userAdminRepository.save(testAdmin1);
        PersonList savedList = personListRepository.save(testList);
        savedAdmin.addPersonList(savedList);
        userAdminRepository.save(savedAdmin);

        mockMvc.perform(get("/user-admin/{adminId}/person-lists", savedAdmin.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(testList.getName()));
    }

    // Test adding a person list to an admin that does not exist
    @Test
    void shouldReturn404WhenGettingPersonListsOfNonExistentAdmin() throws Exception {
        mockMvc.perform(get("/user-admin/999/person-lists"))
                .andExpect(status().isNotFound());
    }
}