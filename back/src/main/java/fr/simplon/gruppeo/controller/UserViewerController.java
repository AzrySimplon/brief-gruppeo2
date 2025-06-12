package fr.simplon.gruppeo.controller;

import fr.simplon.gruppeo.model.UserViewer;
import fr.simplon.gruppeo.repository.UserViewerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-viewer")
public class UserViewerController {
    private final UserViewerRepository userViewerRepository;

    public UserViewerController(UserViewerRepository userViewerRepository) {
        this.userViewerRepository = userViewerRepository;
    }

    // Create
    @PostMapping
    public ResponseEntity<UserViewer> createUserViewer(@RequestBody UserViewer userViewer) {
        if (userViewer.getPerson() != null) {
            userViewer.setPerson(userViewer.getPerson(), true);
        }
        UserViewer savedUserViewer = userViewerRepository.save(userViewer);
        return ResponseEntity.ok(savedUserViewer);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<UserViewer>> getAllUserViewers() {
        List<UserViewer> userViewers = userViewerRepository.findAll();
        return ResponseEntity.ok(userViewers);
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<UserViewer> getUserViewerById(@PathVariable Long id) {
        return userViewerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<UserViewer> updateUserViewer(@PathVariable Long id, @RequestBody UserViewer userViewerDetails) {
        return userViewerRepository.findById(id)
                .map(existingUserViewer -> {
                    existingUserViewer.setUsername(userViewerDetails.getUsername());
                    
                    // Handle person relationship update
                    if (userViewerDetails.getPerson() != null) {
                        existingUserViewer.setPerson(userViewerDetails.getPerson(), true);
                    } else {
                        // If the new details don't include a person, remove the existing relationship
                        if (existingUserViewer.getPerson() != null) {
                            existingUserViewer.getPerson().setUserViewer(null);
                        }
                        existingUserViewer.setPerson(null);
                    }

                    UserViewer updatedUserViewer = userViewerRepository.save(existingUserViewer);
                    return ResponseEntity.ok(updatedUserViewer);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserViewer(@PathVariable Long id) {
        return userViewerRepository.findById(id)
                .map(userViewer -> {
                    // Remove the bidirectional relationship before deleting
                    if (userViewer.getPerson() != null) {
                        userViewer.getPerson().setUserViewer(null);
                    }
                    userViewerRepository.delete(userViewer);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Find by Username (additional useful endpoint)
    @GetMapping("/username/{username}")
    public ResponseEntity<UserViewer> getUserViewerByUsername(@PathVariable String username) {
        return userViewerRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}