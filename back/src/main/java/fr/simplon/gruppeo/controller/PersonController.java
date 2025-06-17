package fr.simplon.gruppeo.controller;

import fr.simplon.gruppeo.model.Person;
import fr.simplon.gruppeo.repository.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Create
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.ok(savedPerson);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        List<Person> persons = personRepository.findAll();
        return ResponseEntity.ok(persons);
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        return personRepository.findById(id)
                .map(existingPerson -> {
                    existingPerson.setName(personDetails.getName());
                    existingPerson.setGender(personDetails.getGender());
                    existingPerson.setFrench_knowledge(personDetails.getFrench_knowledge());
                    existingPerson.setOld_DWWM(personDetails.getOld_DWWM());
                    existingPerson.setTechnical_knowledge(personDetails.getTechnical_knowledge());
                    existingPerson.setProfile(personDetails.getProfile());
                    existingPerson.setBirth_date(personDetails.getBirth_date());
                    existingPerson.setGroups(personDetails.getGroups());
                    
                    Person updatedPerson = personRepository.save(existingPerson);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}