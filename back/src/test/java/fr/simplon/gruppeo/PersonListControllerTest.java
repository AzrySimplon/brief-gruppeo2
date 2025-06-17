package fr.simplon.gruppeo;

import fr.simplon.gruppeo.model.*;
import fr.simplon.gruppeo.repository.*;
import fr.simplon.gruppeo.controller.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test") //tests are done with h2 Driver
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonListControllerTest {
   @Autowired
   private MockMvc mockMvc;
   @Autowired
   private PersonListRepository personListRepository;
   @Autowired
   private PersonGroupRepository personGroupRepository;
   @Autowired
   private PersonRepository personRepository;
   @Autowired
   private ObjectMapper objectMapper;

   private PersonList testList;
   private PersonGroup testGroup;
   private Person testPerson;
   private Person testPerson2;

   @BeforeEach
   void setUp() {
      // Clear all relationships
      for (Person person : personRepository.findAll()) {
         person.getGroups().clear();
      }
      personRepository.saveAll(personRepository.findAll());

      personRepository.deleteAll();
      personGroupRepository.deleteAll();
      personListRepository.deleteAll();

      testList = new PersonList("TestList", 0);
      testGroup = new PersonGroup("TestGroup", 2);
      testPerson = new Person("TestPerson", Gender.X, 5, true, 2, Profile.NORMAL, LocalDate.now());
      testPerson.setIs_teacher(false);
      testPerson2 = new Person("TestPerson2", Gender.F, 5, true, 2, Profile.SHY, LocalDate.now());
      testPerson2.setIs_teacher(true);
   }

   //Test List creation
   @Test
   void shouldCreateList() throws Exception {
      mockMvc.perform(post("/person-list")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(testList)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.name").value(testList.getName()))
              .andExpect(jsonPath("$.number_of_members").value(testList.getNumber_of_members()));
   }

   //Test getting all lists
   @Test
   void shouldGetAllLists() throws Exception {
      PersonList savedList = personListRepository.save(testList);
      mockMvc.perform(get("/person-list"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[*].name").value(savedList.getName()))
              .andExpect(jsonPath("$[*].number_of_members").value(savedList.getNumber_of_members()));
   }

   //Test getting a list by id
   @Test
   void shouldGetListById() throws Exception {
      PersonList savedList = personListRepository.save(testList);
      mockMvc.perform(get("/person-list/{id}", savedList.getId()))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.name").value(savedList.getName()))
              .andExpect(jsonPath("$.number_of_members").value(savedList.getNumber_of_members()));
   }

   //Test getting a non existent list (by id)
   @Test
   void shouldReturn404WhenGettingNonExistentList() throws Exception {
      mockMvc.perform(get("/person-list/{id}", 999L))
              .andExpect(status().isNotFound());
   }

   //Test updating a list
   @Test
   void shouldUpdateList() throws Exception {
      PersonList savedList = personListRepository.save(testList);
      testList.setName("UpdatedName");
      testList.setNumber_of_members(0);

      mockMvc.perform(put("/person-list/{id}", savedList.getId())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(testList)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.name").value(testList.getName()))
              .andExpect(jsonPath("$.number_of_members").value(savedList.getNumber_of_members()));
   }

   //Test deleting a list
   @Test
   void shouldDeleteList() throws Exception {
      PersonList savedList = personListRepository.save(testList);
      mockMvc.perform(delete("/person-list/{id}", savedList.getId()))
              .andExpect(status().isOk());

      mockMvc.perform(get("/person-list/{id}", savedList.getId()))
              .andExpect(status().isNotFound());
   }

   //Test linking a group to a list
   @Test
   void ShouldAddGroupToList() throws Exception {
      PersonGroup savedGroup = personGroupRepository.save(testGroup);
      PersonList savedList = personListRepository.save(testList);

      savedList.addGroup(savedGroup);
      personListRepository.save(savedList);

      mockMvc.perform(post("/person-list/{id}/add-group", savedList.getId(), savedGroup.getId())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(testGroup)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.groups[0].id").value(savedGroup.getId()));
   }

   //Test adding a student to a list
   @Test
   void ShouldAddMemberToList() throws Exception {
      Person savedPerson = personRepository.save(testPerson);
      PersonList savedList = personListRepository.save(testList);
      mockMvc.perform(post("/person-list/{id}/add-student", savedList.getId(), savedPerson.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(testPerson)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.number_of_members").value(testList.getNumber_of_members()));
   }

   //Test adding a teacher to a list
   @Test
   void ShouldAddTeacherToList() throws Exception {
      Person savedPerson2 = personRepository.save(testPerson2);
      PersonList savedList = personListRepository.save(testList);
      mockMvc.perform(post("/person-list/{id}/add-teacher", savedList.getId(), savedPerson2.getId())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(testPerson2)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.number_of_members").value(testList.getNumber_of_members()));
   }
}
