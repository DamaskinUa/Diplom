package org.example.dipl.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "translate_data")
public class Translate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTranslate;

    @Column(nullable = false)
    private String nameTranslate;

    @Column
    private String descriptionTranslate;

    @Column(nullable = false)
    private LocalDate dataRegistr;

    @ManyToMany(mappedBy = "translates")
    private Set<TitleData> titles;

    // Getters and Setters

    public Long getIdTranslate() {
        return idTranslate;
    }

    public void setIdTranslate(Long idTranslate) {
        this.idTranslate = idTranslate;
    }

    public String getNameTranslate() {
        return nameTranslate;
    }

    public void setNameTranslate(String nameTranslate) {
        this.nameTranslate = nameTranslate;
    }

    public String getDescriptionTranslate() {
        return descriptionTranslate;
    }

    public void setDescriptionTranslate(String descriptionTranslate) {
        this.descriptionTranslate = descriptionTranslate;
    }

    public LocalDate getDataRegistr() {
        return dataRegistr;
    }

    public void setDataRegistr(LocalDate dataRegistr) {
        this.dataRegistr = dataRegistr;
    }

    public Set<TitleData> getTitles() {
        return titles;
    }

    public void setTitles(Set<TitleData> titles) {
        this.titles = titles;
    }
}

