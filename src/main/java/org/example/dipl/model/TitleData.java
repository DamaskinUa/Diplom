package org.example.dipl.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "title_data")
public class TitleData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTitle;

    private String nameTitle;
    private String imageTitle;
    @Column(columnDefinition = "TEXT")
    private String descriptionTitle;
    private LocalDate dateReleaseTitle;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusData status;


    @OneToMany(mappedBy = "title")
    private Set<Chapter> chapters;

    @ManyToMany(mappedBy = "markedTitles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
            name = "title_artist",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists= new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "title_genre",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "title_screenwriter",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "screenwriter_id")
    )
    private Set<Screenwriter> screenwriters= new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "title_translate",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "translate_id")
    )
    private Set<Translate> translates= new HashSet<>();

    // Getters and Setters

    public Long getIdTitle() {
        return idTitle;
    }

    public void setIdTitle(Long idTitle) {
        this.idTitle = idTitle;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

    public void setDescriptionTitle(String descriptionTitle) {
        this.descriptionTitle = descriptionTitle;
    }

    public LocalDate getDateReleaseTitle() {
        return dateReleaseTitle;
    }

    public void setDateReleaseTitle(LocalDate dateReleaseTitle) {
        this.dateReleaseTitle = dateReleaseTitle;
    }

    public StatusData getStatus() {
        return status;
    }

    public void setStatus(StatusData status) {
        this.status = status;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Screenwriter> getScreenwriters() {
        return screenwriters;
    }

    public void setScreenwriters(Set<Screenwriter> screenwriters) {
        this.screenwriters = screenwriters;
    }

    public Set<Translate> getTranslates() {
        return translates;
    }

    public void setTranslates(Set<Translate> translates) {
        this.translates = translates;
    }
}

