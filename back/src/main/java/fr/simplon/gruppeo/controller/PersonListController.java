package fr.simplon.gruppeo.controller;

import fr.simplon.gruppeo.model.Person;
import fr.simplon.gruppeo.model.PersonGroup;
import fr.simplon.gruppeo.model.PersonList;
import fr.simplon.gruppeo.repository.PersonListRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/person-list")
public class PersonListController {
   private final PersonListRepository personListRepository;

   public PersonListController(PersonListRepository personListRepository) {
      this.personListRepository = personListRepository;
   }

   // Create
   @PostMapping
   public ResponseEntity<PersonList> createList(@RequestBody PersonList list) {
	  PersonList savedList = personListRepository.save(list);
	  return ResponseEntity.ok(savedList);
   }

   // Read All
   @GetMapping
   public List<PersonList> getAll(){
	  return personListRepository.findAll();
   }

   // Read One
   @GetMapping("/{id}")
   public ResponseEntity<PersonList> getById(@PathVariable Long id) {
	  return personListRepository.findById(id)
			  .map(ResponseEntity::ok)
			  .orElse(ResponseEntity.notFound().build());
   }

   // Add a group in a list
   @PostMapping("/{id}/add-group")
   public ResponseEntity<PersonList> addGroupToList(@PathVariable Long id, @RequestBody PersonGroup group) {
      return personListRepository.findById(id)
              .map(list -> {
                 list.addGroup(group);
                 PersonList updatedList = personListRepository.save(list);
                 return ResponseEntity.ok(updatedList);
              })
              .orElse(ResponseEntity.notFound().build());
   }

   // Add a student in a list
   @PostMapping("/{id}/add-student")
   public ResponseEntity<PersonList> addStudentToList(@PathVariable Long id, @RequestBody Person person) {
       if (person.getIs_teacher()){
           return ResponseEntity.badRequest().build();
       }
	    return personListRepository.findById(id)
			  .map(list -> {
				 list.addMember(person);
				 PersonList updateList = personListRepository.save(list);
				 return ResponseEntity.ok(updateList);
			  })
			  .orElse(ResponseEntity.notFound().build());
   }

    // Add a teacher in a list
    @PostMapping("/{id}/add-teacher")
    public ResponseEntity<PersonList> addTeacherToList(@PathVariable Long id, @RequestBody Person person) {
        if (!person.getIs_teacher()){
            return ResponseEntity.badRequest().build();
        }
        return personListRepository.findById(id)
                .map(list -> {
                    list.addMember(person);
                    PersonList updateList = personListRepository.save(list);
                    return ResponseEntity.ok(updateList);
                })
                .orElse(ResponseEntity.notFound().build());
    }

   // Update
   @PutMapping("/{id}")
   public ResponseEntity<PersonList> updateGroup(@PathVariable Long id, @RequestBody PersonList personListDetails) {
	  return personListRepository.findById(id)
			  .map(existingGroup -> {
				 existingGroup.setName(personListDetails.getName());
				 existingGroup.setNumber_of_members(personListDetails.getNumber_of_members());
				 PersonList updatedGroup = personListRepository.save(existingGroup);
				 return ResponseEntity.ok(updatedGroup);
			  })
			  .orElse(ResponseEntity.notFound().build());
   }

   // Delete
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
	  return personListRepository.findById(id)
			  .map(group -> {
				 personListRepository.delete(group);
				 return ResponseEntity.ok().<Void>build();
			  })
			  .orElse(ResponseEntity.notFound().build());
   }
}
