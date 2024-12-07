package org.example.dipl.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "artist_data")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArtist;
    @Column
    private String nameArtist;
    @Column
    private String descriptioneArtist;

    @ManyToMany(mappedBy = "artists")
    private Set<TitleData> titles;
    // Getters and Setters

    public Long getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(Long idArtist) {
        this.idArtist = idArtist;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public String getDescriptioneArtist() {
        return descriptioneArtist;
    }

    public void setDescriptioneArtist(String descriptioneArtist) {
        this.descriptioneArtist = descriptioneArtist;
    }

    public Set<TitleData> getTitles() {
        return titles;
    }

    public void setTitles(Set<TitleData> titles) {
        this.titles = titles;
    }
}

