package fr.simplon.gruppeo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_admin")
public class UserAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    public UserAdmin() {}

    public UserAdmin(String username) {
        this.username = username;
    }

    @ManyToMany
    @JoinTable(
            name = "admin_person_lists",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "person_list_id")
    )
    @JsonIgnoreProperties("admins")
    private Set<PersonList> personLists = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<PersonList> getPersonLists() {
        return personLists;
    }

    public void setPersonLists(Set<PersonList> personLists) {
        this.personLists = personLists;
    }

    // Helper methods for managing the relationship
    public void addPersonList(PersonList personList) {
        this.personLists.add(personList);
        personList.getAdmins().add(this);
    }

    public void removePersonList(PersonList personList) {
        this.personLists.remove(personList);
        personList.getAdmins().remove(this);
    }

}
