package edu.geekhub.example.service.comments.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class CommentDto {

    private UUID id;
    private String commentText;
    private LocalDate createdAt;
    private String username;

    public UUID getId() {
        return id;
    }

    public CommentDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public CommentDto setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public CommentDto setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CommentDto setUsername(String username) {
        this.username = username;
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
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(commentText,
            that.commentText) && Objects.equals(createdAt, that.createdAt)
            && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentText, createdAt, username);
    }
}
