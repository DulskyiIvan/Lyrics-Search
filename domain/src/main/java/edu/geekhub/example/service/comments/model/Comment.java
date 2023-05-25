package edu.geekhub.example.service.comments.model;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.song.model.Song;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(name = "comment_text", nullable = false)
    private String commentText;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private Song song;

    public UUID getId() {
        return id;
    }

    public Comment setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public Comment setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Comment setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Comment setUser(User user) {
        this.user = user;
        return this;
    }

    public Song getSong() {
        return song;
    }

    public Comment setSong(Song song) {
        this.song = song;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(commentText,
            comment.commentText) && Objects.equals(createdAt, comment.createdAt)
            && Objects.equals(user, comment.user) && Objects.equals(song,
            comment.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentText, createdAt, user, song);
    }
}
