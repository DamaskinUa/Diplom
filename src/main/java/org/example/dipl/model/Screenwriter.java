package org.example.dipl.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "screenwriter_data")
public class Screenwriter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idScreenwriter;

    @Column(nullable = false)
    private String nameScreenwriter;
    @Column
    private String descriptionScreenwriter;

    @ManyToMany(mappedBy = "screenwriters")
    private Set<TitleData> titles;

    // Getters and Setters

    public Long getIdScreenwriter() {
        return idScreenwriter;
    }

    public void setIdScreenwriter(Long idScreenwriter) {
        this.idScreenwriter = idScreenwriter;
    }

    public String getNameScreenwriter() {
        return nameScreenwriter;
    }

    public void setNameScreenwriter(String nameScreenwriter) {
        this.nameScreenwriter = nameScreenwriter;
    }

    public String getDescriptionScreenwriter() {
        return descriptionScreenwriter;
    }

    public void setDescriptionScreenwriter(String descriptionScreenwriter) {
        this.descriptionScreenwriter = descriptionScreenwriter;
    }

    public Set<TitleData> getTitles() {
        return titles;
    }

    public void setTitles(Set<TitleData> titles) {
        this.titles = titles;
    }
}
