package edu.geekhub.example.service.song.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AddSongDto {

    private String title;
    private String artist;
    private String lyric;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;
    private String youtubeLink;
    private Integer genre;

    public String getTitle() {
        return title;
    }

    public AddSongDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public AddSongDto setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getLyric() {
        return lyric;
    }

    public AddSongDto setLyric(String lyric) {
        this.lyric = lyric;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public AddSongDto setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public AddSongDto setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
        return this;
    }

    public Integer getGenre() {
        return genre;
    }

    public AddSongDto setGenre(Integer genre) {
        this.genre = genre;
        return this;
    }
}
