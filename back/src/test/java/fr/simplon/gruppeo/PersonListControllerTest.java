package fr.simplon.gruppeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.simplon.gruppeo.controller.PersonListController;
import fr.simplon.gruppeo.repository.PersonListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonListControllerTest {
   @Autowired
   private MockMvc mockMvc;
   @Autowired
   private PersonListRepository personListRepository;
   @Autowired
   private ObjectMapper objectMapper;

   //Test List creation
   //Test getting all lists
   //Test getting a list by id
   //Test getting a non existent list (by id)
   //Test updating a list (?)
   //Test deleting a list
   //Test linking a group to a list
   //Test linking multiple groups to a list
   //Test adding a member to a list
}
