package org.example.dipl.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "status_data")
public class StatusData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStatus;

    @Column(nullable = false)
    private String nameStatus;

    @OneToMany(mappedBy = "status")
    private Set<TitleData> titles;

    // Getters and Setters

    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    public Set<TitleData> getTitles() {
        return titles;
    }

    public void setTitles(Set<TitleData> titles) {
        this.titles = titles;
    }
}

