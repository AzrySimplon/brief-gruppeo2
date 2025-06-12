package fr.simplon.gruppeo.controller;

import fr.simplon.gruppeo.model.Person;
import fr.simplon.gruppeo.model.PersonGroup;
import fr.simplon.gruppeo.repository.PersonGroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person-group")
public class PersonGroupController {
    private final PersonGroupRepository groupRepository;

    public PersonGroupController(PersonGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // Create
    @PostMapping
    public ResponseEntity<PersonGroup> createGroup(@RequestBody PersonGroup group) {
        PersonGroup savedGroup = groupRepository.save(group);
        return ResponseEntity.ok(savedGroup);
    }

    //Add a person to a group
    @PostMapping("/{id}/add-member")
    public ResponseEntity<PersonGroup> addMemberToGroup(@PathVariable Long id, @RequestBody Person person) {
        return groupRepository.findById(id)
                .map(group -> {
                    group.addMember(person);
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