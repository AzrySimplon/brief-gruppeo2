package fr.simplon.gruppeo.repository;

import fr.simplon.gruppeo.model.PersonList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonListRepository extends JpaRepository<PersonList, Long> {
}
