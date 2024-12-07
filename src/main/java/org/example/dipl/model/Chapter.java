package org.example.dipl.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "chapter_data")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChapter;

    private String nameChapter;
    private Integer numberChapter;
    private LocalDate dateReleaseChapter;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private TitleData title;

    @OneToMany(mappedBy = "chapter")
    private Set<Page> pages;

    // Getters and Setters

    public Long getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(Long idChapter) {
        this.idChapter = idChapter;
    }

    public String getNameChapter() {
        return nameChapter;
    }

    public void setNameChapter(String nameChapter) {
        this.nameChapter = nameChapter;
    }

    public Integer getNumberChapter() {
        return numberChapter;
    }

    public void setNumberChapter(Integer numberChapter) {
        this.numberChapter = numberChapter;
    }

    public LocalDate getDateReleaseChapter() {
        return dateReleaseChapter;
    }

    public void setDateReleaseChapter(LocalDate dateReleaseChapter) {
        this.dateReleaseChapter = dateReleaseChapter;
    }

    public TitleData getTitle() {
        return title;
    }

    public void setTitle(TitleData title) {
        this.title = title;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }
}

