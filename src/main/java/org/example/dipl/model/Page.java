package org.example.dipl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "page_data")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPage;

    private String numberPage;
    private String imagePage;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    // Getters and Setters

    public Long getIdPage() {
        return idPage;
    }

    public void setIdPage(Long idPage) {
        this.idPage = idPage;
    }

    public String getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(String numberPage) {
        this.numberPage = numberPage;
    }

    public String getImagePage() {
        return imagePage;
    }

    public void setImagePage(String imagePage) {
        this.imagePage = imagePage;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}

