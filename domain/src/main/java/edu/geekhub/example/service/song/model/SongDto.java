package edu.geekhub.example.service.song.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

public class SongDto {

    private UUID id;
    private String title;
    private String artist;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;
    private String youtubeLink;

    private Long totalViews;

    public UUID getId() {
        return id;
    }

    public SongDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public Long getTotalViews() {
        return totalViews;
    }

    public SongDto setTotalViews(Long totalViews) {
        this.totalViews = totalViews;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SongDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public SongDto setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public SongDto setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public SongDto setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
        return this;
    }
}
