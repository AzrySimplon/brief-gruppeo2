package fr.simplon.gruppeo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "user_viewer")
public class UserViewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonIgnoreProperties("person_id")
    private Person person;

    // Constructors
    public UserViewer() {}

    public UserViewer(String username, Person person) {
        this.username = username;
        this.person = person;
    }

    // Getters and Setters
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPerson(Person person, boolean setOtherSide) {
        this.person = person;
        if (setOtherSide && person != null) {
            person.setUserViewer(this);
        }
    }
}