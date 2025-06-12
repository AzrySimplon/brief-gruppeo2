package fr.simplon.gruppeo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    private Integer french_knowledge;
    private Boolean old_DWWM;
    private Integer technical_knowledge;
    
    @Enumerated(EnumType.STRING)
    private Profile profile;
    
    private LocalDate birth_date;

    @ManyToMany(mappedBy = "members")
    @JsonIgnoreProperties("members")
    private Set<PersonGroup> groups = new HashSet<>();


    public Person() {
    }

    public Person(String name, Gender gender, Integer french_knowledge, Boolean old_DWWM, Integer technical_knowledge, Profile profile, LocalDate birth_date) {
        this.name = name;
        this.gender = gender;
        this.french_knowledge = french_knowledge;
        this.old_DWWM = old_DWWM;
        this.technical_knowledge = technical_knowledge;
        this.profile = profile;
        this.birth_date = birth_date;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getFrench_knowledge() {
        return french_knowledge;
    }

    public void setFrench_knowledge(Integer french_knowledge) {
        this.french_knowledge = french_knowledge;
    }

    public Boolean getOld_DWWM() {
        return old_DWWM;
    }

    public void setOld_DWWM(Boolean old_DWWM) {
        this.old_DWWM = old_DWWM;
    }

    public Integer getTechnical_knowledge() {
        return technical_knowledge;
    }

    public void setTechnical_knowledge(Integer technical_knowledge) {
        this.technical_knowledge = technical_knowledge;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public Set<PersonGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<PersonGroup> groups) {
        this.groups = groups;
    }
}