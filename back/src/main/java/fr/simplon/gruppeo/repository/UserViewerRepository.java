package fr.simplon.gruppeo.repository;

import fr.simplon.gruppeo.model.UserViewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserViewerRepository extends JpaRepository<UserViewer, Long> {
    Optional<UserViewer> findByUsername(String username);
}

