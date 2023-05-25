package edu.geekhub.example.service.lyric.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class LyricDto {

    private UUID id;
    private String text;
    private LocalDate createdAt;

    private UUID songId;

    private UUID userId;

    public UUID getId() {
        return id;
    }

    public LyricDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public LyricDto setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LyricDto setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UUID getSongId() {
        return songId;
    }

    public LyricDto setSongId(UUID songId) {
        this.songId = songId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LyricDto setUserId(UUID userId) {
        this.userId = userId;
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
        LyricDto lyricDto = (LyricDto) o;
        return Objects.equals(id, lyricDto.id) && Objects.equals(text,
            lyricDto.text) && Objects.equals(createdAt, lyricDto.createdAt)
            && Objects.equals(songId, lyricDto.songId) && Objects.equals(userId,
            lyricDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createdAt, songId, userId);
    }
}
