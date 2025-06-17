package fr.simplon.gruppeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.simplon.gruppeo.model.*;
import fr.simplon.gruppeo.repository.PersonGroupRepository;
import fr.simplon.gruppeo.repository.PersonListRepository;
import fr.simplon.gruppeo.repository.PersonRepository;
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
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test") //tests are done with h2 Driver
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonGroupControllerTest {
   @Autowired
   private MockMvc mockMvc;
   @Autowired
   private PersonGroupRepository personGroupRepository;
   @Autowired
   private PersonListRepository personListRepository;
   @Autowired
   private PersonRepository personRepository;
   @Autowired
   private ObjectMapper objectMapper;

   private PersonGroup testGroup;
   private PersonList testList;
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

	  testGroup = new PersonGroup("TestGroup", 0);

	  testList = new PersonList("TestList", 0);

	  testPerson = new Person("TestPerson", Gender.X, 5, true, 2, Profile.NORMAL, LocalDate.now());
	  testPerson.setIs_teacher(false);
	  testPerson2 = new Person("TestPerson2", Gender.F, 5, true, 2, Profile.SHY, LocalDate.now());
	  testPerson2.setIs_teacher(true);
   }

   //Test Group creation
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void shouldCreateGroup() throws Exception {
	  mockMvc.perform(post("/person-group")
					  .contentType(MediaType.APPLICATION_JSON)
					  .content(objectMapper.writeValueAsString(testGroup)))
			  .andExpect(status().isOk())
			  .andExpect(jsonPath("$.name").value(testGroup.getName()))
			  .andExpect(jsonPath("$.number_of_members").value(testGroup.getNumber_of_members()));
   }

   //Test getting all groups
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void shouldGetAllGroups() throws Exception {
	  PersonGroup savedGroup = personGroupRepository.save(testGroup);
	  mockMvc.perform(get("/person-group"))
			  .andExpect(status().isOk())
			  .andExpect(jsonPath("$", hasSize(1)))
			  .andExpect(jsonPath("$[*].name").value(savedGroup.getName()))
			  .andExpect(jsonPath("$[*].number_of_members").value(savedGroup.getNumber_of_members()));
   }

   //Test getting a group by id
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void shouldGetGroupById() throws Exception {
	  PersonGroup savedGroup = personGroupRepository.save(testGroup);
	  mockMvc.perform(get("/person-group/{id}", savedGroup.getId()))
			  .andExpect(status().isOk())
			  .andExpect(jsonPath("$.name").value(savedGroup.getName()))
			  .andExpect(jsonPath("$.number_of_members").value(savedGroup.getNumber_of_members()));
   }

   //Test getting a non existent group (by id)
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void shouldReturn404WhenGettingNonExistentGroup() throws Exception {
	  mockMvc.perform(get("/person-group/{id}", 999L))
			  .andExpect(status().isNotFound());
   }

   //Test updating a group
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void shouldUpdateGroup() throws Exception {
	  PersonGroup savedGroup = personGroupRepository.save(testGroup);
	  testGroup.setName("UpdatedName");
	  testGroup.setNumber_of_members(0);

	  mockMvc.perform(put("/person-group/{id}", savedGroup.getId())
					  .contentType(MediaType.APPLICATION_JSON)
					  .content(objectMapper.writeValueAsString(testGroup)))
			  .andExpect(status().isOk())
			  .andExpect(jsonPath("$.name").value(testGroup.getName()))
			  .andExpect(jsonPath("$.number_of_members").value(savedGroup.getNumber_of_members()));
   }

   //Test deleting a group
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void shouldDeleteGroup() throws Exception {
	  PersonGroup savedGroup = personGroupRepository.save(testGroup);
	  mockMvc.perform(delete("/person-group/{id}", savedGroup.getId()))
			  .andExpect(status().isOk());

	  mockMvc.perform(get("/person-group/{id}", savedGroup.getId()))
			  .andExpect(status().isNotFound());
   }

   //Test adding a valid member to a group (member already in a list)
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void ShouldAddMemberToGroup() throws Exception {
	  PersonGroup savedGroup = personGroupRepository.save(testGroup);
	  Person savedPerson = personRepository.save(testPerson);
	  PersonList savedList = personListRepository.save(testList);

	  savedList.addGroup(savedGroup);
	  personListRepository.save(savedList);
	  savedPerson.addList(savedList);
	  personRepository.save(savedPerson);

	  mockMvc.perform(post("/person-group/{id}/add-member/{personId}", savedGroup.getId(), savedPerson.getId())
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(testPerson)))
			  .andExpect(status().isOk())
			  .andExpect(jsonPath("$.number_of_members").value(testGroup.getNumber_of_members()));
   }


   //Test adding a member to a group WITHOUT the member being in a list
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void ShouldNotAddMemberToGroup() throws Exception {
	  PersonGroup savedGroup = personGroupRepository.save(testGroup);
	  Person savedPerson = personRepository.save(testPerson);
	  mockMvc.perform(post("/person-group/{id}/add-member/{personId}", savedGroup.getId(), savedPerson.getId())
					  .contentType(MediaType.APPLICATION_JSON)
					  .content(objectMapper.writeValueAsString(testPerson)))
			  .andExpect(status().isNotFound());
   }

   //Test setting a list to a group
   @Test
   @WithMockUser(username = "testUser", password = "pass")
   void ShouldAddGroupToList() throws Exception {
	  PersonGroup savedGroup = personGroupRepository.save(testGroup);
	  PersonList savedList = personListRepository.save(testList);
	  mockMvc.perform(post("/person-group/{id}/set-list", savedGroup.getId())
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(testGroup)))
			  .andExpect(status().isOk())
			  .andExpect(jsonPath("$.list.id").value(savedGroup.getList().getId()));
   }
}
