package edu.geekhub.example.service.proposeEditLyric.model;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.song.model.Song;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class ProposeEditLyric {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(name = "lyric_text")
    private String lyricText;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private Song song;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UUID getId() {
        return id;
    }

    public ProposeEditLyric setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getLyricText() {
        return lyricText;
    }

    public ProposeEditLyric setLyricText(String lyricText) {
        this.lyricText = lyricText;
        return this;
    }

    public Song getSong() {
        return song;
    }

    public ProposeEditLyric setSong(Song song) {
        this.song = song;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ProposeEditLyric setUser(User user) {
        this.user = user;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ProposeEditLyric setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
