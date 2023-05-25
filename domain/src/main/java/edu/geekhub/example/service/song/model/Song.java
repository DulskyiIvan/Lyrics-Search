package edu.geekhub.example.service.song.model;

import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.lyric.model.Lyric;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(name = "title")
    private String title;
    @Column(name = "releaseDate")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;
    @Nullable
    private String youtubeLink;
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private Artist artist;
    @OneToMany(mappedBy = "song")
    private List<Lyric> lyricVersions;


    public UUID getId() {
        return id;
    }

    public Song setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Song setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Song setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public Song setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Song setGenre(Genre genre) {
        this.genre = genre;
        return this;

    }

    public Artist getArtist() {
        return artist;
    }

    public Song setArtist(Artist artist) {
        this.artist = artist;
        return this;
    }

    public List<Lyric> getLyricVersions() {
        return lyricVersions;
    }

    public Song setLyricVersions(
        List<Lyric> lyricVersions) {
        this.lyricVersions = lyricVersions;
        return this;
    }
}
