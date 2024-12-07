package org.example.dipl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "marker_user_data")
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarker;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private TitleData title;

    // Getters and Setters

    public Long getIdMarker() {
        return idMarker;
    }

    public void setIdMarker(Long idMarker) {
        this.idMarker = idMarker;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TitleData getTitle() {
        return title;
    }

    public void setTitle(TitleData title) {
        this.title = title;
    }
}
