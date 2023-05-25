package edu.geekhub.example.service.song.controller;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.service.ArtistService;
import edu.geekhub.example.service.genre.service.GenreService;
import edu.geekhub.example.service.song.model.AddSongDto;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.model.SongDto;
import edu.geekhub.example.service.song.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class SongController {

    private final SongService songService;
    private final GenreService genreService;
    private final ArtistService artistService;

    public SongController(SongService songService, GenreService genreService,
        ArtistService artistService) {
        this.songService = songService;
        this.genreService = genreService;
        this.artistService = artistService;
    }

    @GetMapping("/song-add")
    public String getSongAddPage(Model model) {
        model.addAttribute("song", new AddSongDto());
        model.addAttribute("genres", genreService.getAll());
        return "main/addSong";
    }

    @GetMapping("/charts")
    public String getPaginatedChartsOfSong(
        @RequestParam(value = "period", defaultValue = "day") String period,
        @RequestParam(value = "genreId", defaultValue = "0") int genreId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<SongDto> songPage = songService.getSortedSongs(period, genreId, pageable);
        List<SongDto> songs = songPage.getContent();
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("songs", songs);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", songPage.getTotalPages());

        return "main/charts";
    }

    @GetMapping("/song")
    public String getSongById(@RequestParam(value = "id") UUID songId,
        Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Song song = songService.getSongById(songId, user);

        model.addAttribute("song", song);
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("lyrics", song.getLyricVersions());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userRole", user.getRole());
        return "main/song";
    }

    @GetMapping("/search")
    public String getSearchPage(@RequestParam("query") String query,
        Model model) {
        List<SongDto> songs = songService.getSongsByQuery(query);
        model.addAttribute("query", query);
        model.addAttribute("songs", songs);
        return "main/searchSong";
    }

    @GetMapping("/all-songs")
    public String getPageAllSongs() {
        return "main/allSongs";
    }

    @GetMapping("/artist-all-songs")
    public String getPageAllSongsOfArtist(Model model, @RequestParam("artistId") UUID artistId) {
        Artist artist = artistService.getArtistById(artistId);
        model.addAttribute("artistId", artistId);
        model.addAttribute("artist", artist);
        return "main/allSongOfArtist";
    }
}
