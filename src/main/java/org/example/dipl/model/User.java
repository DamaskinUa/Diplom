package org.example.dipl.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user_data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(unique = true)
    private String loginUser;

    private String passwordUser;

    @Column(unique = true)
    private String email;

    private LocalDate dataRegistri;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleUser role;


    @ManyToMany
    @JoinTable(
            name = "marker_user_data",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id")
    )
    private Set<TitleData> markedTitles;

    // Getters and Setters

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataRegistri() {
        return dataRegistri;
    }

    public void setDataRegistri(LocalDate dataRegistri) {
        this.dataRegistri = dataRegistri;
    }

    public RoleUser getRole() {
        return role;
    }

    public void setRole(RoleUser role) {
        this.role = role;
    }


    public Set<TitleData> getMarkedTitles() {
        return markedTitles;
    }

    public void setMarkedTitles(Set<TitleData> markedTitles) {
        this.markedTitles = markedTitles;
    }
}
