package fr.simplon.gruppeo.controller;

import fr.simplon.gruppeo.model.PersonList;
import fr.simplon.gruppeo.model.UserAdmin;
import fr.simplon.gruppeo.repository.PersonListRepository;
import fr.simplon.gruppeo.repository.UserAdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user-admin")
public class UserAdminController {
    private final UserAdminRepository userAdminRepository;
    private final PersonListRepository personListRepository;

    public UserAdminController(UserAdminRepository userAdminRepository, 
                             PersonListRepository personListRepository) {
        this.userAdminRepository = userAdminRepository;
        this.personListRepository = personListRepository;
    }

    // Create
    @PostMapping
    public ResponseEntity<UserAdmin> createUserAdmin(@RequestBody UserAdmin userAdmin) {
        UserAdmin savedUserAdmin = userAdminRepository.save(userAdmin);
        return ResponseEntity.ok(savedUserAdmin);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<UserAdmin>> getAllUserAdmins() {
        List<UserAdmin> userAdmins = userAdminRepository.findAll();
        return ResponseEntity.ok(userAdmins);
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<UserAdmin> getUserAdminById(@PathVariable Long id) {
        return userAdminRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<UserAdmin> updateUserAdmin(@PathVariable Long id, 
                                                   @RequestBody UserAdmin userAdminDetails) {
        return userAdminRepository.findById(id)
                .map(existingUserAdmin -> {
                    existingUserAdmin.setUsername(userAdminDetails.getUsername());
                    UserAdmin updatedUserAdmin = userAdminRepository.save(existingUserAdmin);
                    return ResponseEntity.ok(updatedUserAdmin);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAdmin(@PathVariable Long id) {
        return userAdminRepository.findById(id)
                .map(userAdmin -> {
                    // Remove all relationships with PersonLists before deleting
                    Set<PersonList> lists = userAdmin.getPersonLists();
                    lists.forEach(list -> userAdmin.removePersonList(list));
                    
                    userAdminRepository.delete(userAdmin);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Add PersonList to UserAdmin
    @PostMapping("/{adminId}/person-lists/{listId}")
    public ResponseEntity<UserAdmin> addPersonListToAdmin(
            @PathVariable Long adminId,
            @PathVariable Long listId) {
        UserAdmin userAdmin = userAdminRepository.findById(adminId)
                .orElse(null);
        PersonList personList = personListRepository.findById(listId)
                .orElse(null);

        if (userAdmin == null || personList == null) {
            return ResponseEntity.notFound().build();
        }

        userAdmin.addPersonList(personList);
        UserAdmin savedAdmin = userAdminRepository.save(userAdmin);
        return ResponseEntity.ok(savedAdmin);
    }

    // Remove PersonList from UserAdmin
    @DeleteMapping("/{adminId}/person-lists/{listId}")
    public ResponseEntity<UserAdmin> removePersonListFromAdmin(
            @PathVariable Long adminId,
            @PathVariable Long listId) {
        UserAdmin userAdmin = userAdminRepository.findById(adminId)
                .orElse(null);
        PersonList personList = personListRepository.findById(listId)
                .orElse(null);

        if (userAdmin == null || personList == null) {
            return ResponseEntity.notFound().build();
        }

        userAdmin.removePersonList(personList);
        UserAdmin savedAdmin = userAdminRepository.save(userAdmin);
        return ResponseEntity.ok(savedAdmin);
    }

    // Get all PersonLists for a UserAdmin
    @GetMapping("/{adminId}/person-lists")
    public ResponseEntity<Set<PersonList>> getAdminPersonLists(@PathVariable Long adminId) {
        return userAdminRepository.findById(adminId)
                .map(userAdmin -> ResponseEntity.ok(userAdmin.getPersonLists()))
                .orElse(ResponseEntity.notFound().build());
    }
}