package fr.simplon.gruppeo.repository;

import fr.simplon.gruppeo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}