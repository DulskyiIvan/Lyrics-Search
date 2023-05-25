package edu.geekhub.example.service.song.model;

import edu.geekhub.example.authentication.user.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "views")
public class SongView {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "viewed_date")
    private LocalDate viewedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public UUID getId() {
        return id;
    }

    public SongView setId(UUID id) {
        this.id = id;
        return this;
    }

    public Song getSong() {
        return song;
    }

    public SongView setSong(Song song) {
        this.song = song;
        return this;
    }

    public LocalDate getViewedDate() {
        return viewedDate;
    }

    public SongView setViewedDate(LocalDate viewedDate) {
        this.viewedDate = viewedDate;
        return this;
    }

    public User getUser() {
        return user;
    }

    public SongView setUser(User user) {
        this.user = user;
        return this;
    }
}
