package fr.simplon.gruppeo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person_group")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PersonGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer number_of_members;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> members = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "list_id")
    private PersonList lists;

    public PersonGroup() {
    }

    public PersonGroup(String name, Integer number_of_members) {
        this.name = name;
        this.number_of_members = number_of_members;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber_of_members() {
        return number_of_members;
    }

    public void setNumber_of_members(Integer number_of_members) {
        this.number_of_members = number_of_members;
    }

    public void updateNumberOfMembers() {
        this.number_of_members = members.size();
    }

    public void addMember(Person person) {
        this.members.add(person);
        updateNumberOfMembers();
    }

    public Set<Person> getMembers() {
        return members;
    }

    public void setMembers(Set<Person> members) {
        this.members = members;
        updateNumberOfMembers();
    }

    public PersonList getList() {
        return lists;
    }

    public void setList(PersonList list) {
        this.lists = list;
        if (list != null) {
            list.getGroups().add(this);
        }
    }
}