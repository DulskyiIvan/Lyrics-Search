package edu.geekhub.example.service.song.service;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.registration.ValidationResult;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.service.ArtistService;
import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.genre.service.GenreService;
import edu.geekhub.example.service.lyric.model.Lyric;
import edu.geekhub.example.service.lyric.repository.LyricRepository;
import edu.geekhub.example.service.song.model.AddSongDto;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.model.SongDto;
import edu.geekhub.example.service.song.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final LyricRepository lyricRepository;
    private final GenreService genreService;
    private final ArtistService artistService;
    private final SongViewService songViewService;
    private final SongValidator songValidator;
    private final SongDtoMapper mapper;

    public SongService(SongRepository songRepository,
        LyricRepository lyricRepository, GenreService genreService, ArtistService artistService,
        SongViewService songViewService, SongValidator songValidator, SongDtoMapper mapper) {
        this.songRepository = songRepository;
        this.lyricRepository = lyricRepository;
        this.genreService = genreService;
        this.artistService = artistService;
        this.songViewService = songViewService;
        this.songValidator = songValidator;
        this.mapper = mapper;
    }

    public ValidationResult validateSongDto(AddSongDto song) {
        return songValidator.validate(song);
    }

    public Page<SongDto> getPageAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable)
            .map(mapper);
    }

    public Page<SongDto> getPageSongsByQuery(String query, Pageable pageable) {
        return songRepository.findAllByTitleContainingIgnoreCaseOrArtistNameContainingIgnoreCase(
                query, query, pageable)
            .map(mapper);
    }


    public SongDto saveSong(AddSongDto addSongDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        addArtistIfNotExist(addSongDto.getArtist());

        Song song = new Song();
        song.setTitle(addSongDto.getTitle());
        song.setReleaseDate(addSongDto.getReleaseDate());
        if (!addSongDto.getYoutubeLink().isBlank()) {
            song.setYoutubeLink(addSongDto.getYoutubeLink());
        }
        song.setGenre(genreService.getGenreById(addSongDto.getGenre()));
        song.setArtist(artistService.getArtistByName(addSongDto.getArtist()));

        Lyric lyric = new Lyric();
        lyric.setSong(song);
        lyric.setText(addSongDto.getLyric());
        lyric.setCreatedAt(LocalDate.now());
        lyric.setUser(user);

        Song addedSong = songRepository.save(song);
        lyricRepository.save(lyric);
        return mapper.apply(addedSong);
    }

    public void updateSong(Song song, User user, Lyric lyric) {
        addArtistIfNotExist(song.getArtist().getName());
        songRepository.save(song);
        lyric.setSong(song);
        lyric.setUser(user);
        lyric.setCreatedAt(LocalDate.now());
        lyricRepository.save(lyric);
    }

    private void addArtistIfNotExist(String name) {
        if (!artistService.existsArtistByName(name)) {
            Artist artist = new Artist();
            artist.setName(name);
            artistService.save(artist);
        }
    }

    public Song getSongById(UUID songId, User user) {
        Song song = songRepository.findById(songId)
            .orElseThrow(
                () -> new NotFoundException("Song with this id not found")
            );
        if (song != null) {
            songViewService.addSongView(song, user);
        }
        return song;
    }

    public Page<SongDto> getSortedSongs(String period, Integer genreId, Pageable pageable) {
        List<Genre> genres = getGenresForSorted(genreId);
        switch (period) {
            case "year" -> {
                Page<Song> songPage = songRepository.getTopSongsByViewsAndYearAndGenre(
                    LocalDate.now().minusYears(1), genres, pageable);
                return songPage.map(mapper);
            }
            case "month" -> {
                Page<Song> songPage = songRepository.getTopSongsByViewsAndMonth(
                    LocalDate.now().minusMonths(1), genres, pageable);
                return songPage.map(mapper);
            }
            case "week" -> {
                Page<Song> songPage = songRepository.getTopSongsByViewsAndWeek(
                    LocalDate.now().minusWeeks(1),
                    genres, pageable);
                return songPage.map(mapper);
            }
            case "day" -> {
                Page<Song> songPage = songRepository.getTopSongsByViewsAndDay(LocalDate.now(),
                    genres, pageable);
                return songPage.map(mapper);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private List<Genre> getGenresForSorted(Integer genreId) {
        if (genreId == 0) {
            return genreService.getAll();
        } else {
            List<Genre> genres = new ArrayList<>();
            genres.add(genreService.getGenreById(genreId));
            return genres;
        }
    }

    public void delete(UUID songId) {
        songRepository.findById(songId)
            .ifPresentOrElse(song -> songRepository.deleteById(songId), () -> {
                throw new NotFoundException("Song with id" + songId + " not found");
            });
    }

    public List<SongDto> getSongsByQuery(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }
        return songRepository.searchByTitleAndArtistName(query)
            .stream()
            .map(mapper)
            .toList();
    }

    public List<Song> getSongsByGenre(Genre genre) {
        return songRepository.findAllByGenre(genre);
    }

    public List<Song> getSongsByArtistId(UUID id) {
        return songRepository.findAllByArtistId(id);
    }

    public Page<SongDto> getPageAllSongsByArtistId(Pageable pageable, UUID artistId) {
        return songRepository.findAllByArtistId(pageable, artistId)
            .map(mapper);
    }

    public SongDto updateGenre(Song song) {
        return mapper
            .apply(songRepository.save(songRepository.findById(song.getId())
                .orElseThrow(
                    () -> new NotFoundException("Song with id " + song.getId() + " not found"))
                .setGenre(genreService.getGenreById(song.getGenre().getId()))));
    }
}
