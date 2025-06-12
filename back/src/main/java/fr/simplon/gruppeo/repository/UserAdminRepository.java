package fr.simplon.gruppeo.repository;

import fr.simplon.gruppeo.model.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAdminRepository extends JpaRepository<UserAdmin, Long> {
}
