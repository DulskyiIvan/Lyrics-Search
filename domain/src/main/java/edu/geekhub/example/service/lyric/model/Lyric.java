package edu.geekhub.example.service.lyric.model;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.song.model.Song;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lyrics")
public class Lyric {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private Song song;
    @ManyToOne
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    private User user;

    public UUID getId() {
        return id;
    }

    public Lyric setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Lyric setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Lyric setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Song getSong() {
        return song;
    }

    public Lyric setSong(Song song) {
        this.song = song;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Lyric setUser(User user) {
        this.user = user;
        return this;
    }
}
