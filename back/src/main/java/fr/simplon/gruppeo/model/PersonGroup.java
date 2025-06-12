package fr.simplon.gruppeo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "person_group")
public class PersonGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer number_of_members;

    @OneToMany
    @JoinColumn(name = "person_id")
    private List<Integer> members_ids;

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

    public List<Integer> getMembersIds() {
        return members_ids;
    }

    public void setMembers(List<Integer> members) {
        this.members_ids = members;
    }
}
