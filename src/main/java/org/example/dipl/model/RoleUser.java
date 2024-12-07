package org.example.dipl.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role_user")
public class RoleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(unique = true, nullable = false)
    private String nameRole;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    // Getters and Setters

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

