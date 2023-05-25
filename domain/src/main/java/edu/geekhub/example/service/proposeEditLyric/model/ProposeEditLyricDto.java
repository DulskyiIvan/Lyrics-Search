package edu.geekhub.example.service.proposeEditLyric.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ProposeEditLyricDto {

    private UUID id;
    private String lyricText;
    private LocalDate createdAt;
    private String songId;
    private String songTitle;
    private String artistName;

    public UUID getId() {
        return id;
    }

    public ProposeEditLyricDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getLyricText() {
        return lyricText;
    }

    public ProposeEditLyricDto setLyricText(String lyricText) {
        this.lyricText = lyricText;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ProposeEditLyricDto setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getSongId() {
        return songId;
    }

    public ProposeEditLyricDto setSongId(String songId) {
        this.songId = songId;
        return this;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public ProposeEditLyricDto setSongTitle(String songTitle) {
        this.songTitle = songTitle;
        return this;
    }

    public String getArtistName() {
        return artistName;
    }

    public ProposeEditLyricDto setArtistName(String artistName) {
        this.artistName = artistName;
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
        ProposeEditLyricDto that = (ProposeEditLyricDto) o;
        return Objects.equals(id, that.id) && Objects.equals(lyricText,
            that.lyricText) && Objects.equals(createdAt, that.createdAt)
            && Objects.equals(songId, that.songId) && Objects.equals(songTitle,
            that.songTitle) && Objects.equals(artistName, that.artistName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lyricText, createdAt, songId, songTitle, artistName);
    }
}
