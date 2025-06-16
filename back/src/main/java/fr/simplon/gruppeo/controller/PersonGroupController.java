package fr.simplon.gruppeo.controller;

import fr.simplon.gruppeo.model.Person;
import fr.simplon.gruppeo.model.PersonGroup;
import fr.simplon.gruppeo.model.PersonList;
import fr.simplon.gruppeo.repository.PersonGroupRepository;
import fr.simplon.gruppeo.repository.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/person-group")
public class PersonGroupController {
    private final PersonGroupRepository groupRepository;
    private final PersonRepository personRepository;

    public PersonGroupController(PersonGroupRepository groupRepository, PersonRepository personRepository) {
        this.groupRepository = groupRepository;
        this.personRepository = personRepository;
    }

    // Create
    @PostMapping
    public ResponseEntity<PersonGroup> createGroup(@RequestBody PersonGroup group) {
        PersonGroup savedGroup = groupRepository.save(group);
        return ResponseEntity.ok(savedGroup);
    }

    //Add a person to a group
    @PostMapping("/{id}/add-member/{personId}")
    public ResponseEntity<PersonGroup> addMemberToGroup(@PathVariable Long id, @PathVariable Long personId) {
        Person person = personRepository.findById(personId).orElse(null);
        //Get group's list id
        Long groupListId = Objects.requireNonNull(groupRepository.findById(id).orElse(null)).getList().getId();

        if (person == null || groupListId == null) {
            return ResponseEntity.notFound().build();
        }

        //Check if the group has a list in common with the person
        System.out.println(person.getLists().stream().anyMatch(l -> l.getId().equals(groupListId)));
        if (person.getLists().stream().anyMatch(l -> l.getId().equals(groupListId))) {
            return groupRepository.findById(id)
                    .map(group -> {
                        group.addMember(person);
                        PersonGroup updatedGroup = groupRepository.save(group);
                        return ResponseEntity.ok(updatedGroup);
                    })
                    .orElse(ResponseEntity.notFound().build());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    //Set the list to a group
    @PostMapping("/{id}/set-list")
    public ResponseEntity<PersonGroup> setListForGroup(@PathVariable Long id, @RequestBody PersonList list) {
        return groupRepository.findById(id)
                .map(group -> {
                    group.setList(list);
                    PersonGroup updatedGroup = groupRepository.save(group);
                    return ResponseEntity.ok(updatedGroup);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Read All
    @GetMapping
    public List<PersonGroup> getAll(){
        return groupRepository.findAll();
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<PersonGroup> getById(@PathVariable Long id) {
        return groupRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<PersonGroup> updateGroup(@PathVariable Long id, @RequestBody PersonGroup groupDetails) {
        return groupRepository.findById(id)
                .map(existingGroup -> {
                    existingGroup.setName(groupDetails.getName());
                    existingGroup.setNumber_of_members(groupDetails.getNumber_of_members());
                    PersonGroup updatedGroup = groupRepository.save(existingGroup);
                    return ResponseEntity.ok(updatedGroup);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return groupRepository.findById(id)
                .map(group -> {
                    groupRepository.delete(group);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
