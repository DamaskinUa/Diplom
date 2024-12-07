package org.example.dipl.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre_title")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenre;

    private String nameGenre;

    @ManyToMany(mappedBy = "genres")
    private Set<TitleData> titles;

    // Getters and Setters

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    public Set<TitleData> getTitles() {
        return titles;
    }

    public void setTitles(Set<TitleData> titles) {
        this.titles = titles;
    }
}

