package edu.geekhub.example.service.song.controller;

import edu.geekhub.example.registration.ValidationResult;
import edu.geekhub.example.service.song.model.AddSongDto;
import edu.geekhub.example.service.song.model.SongDto;
import edu.geekhub.example.service.song.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class SongRestController {

    private final SongService songService;

    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/search-result")
    public ResponseEntity<List<SongDto>> getSearchResult(@RequestParam("query") String query) {
        List<SongDto> songs = songService.getSongsByQuery(query);
        return ResponseEntity.ok(songs);
    }

    @PostMapping("/save-song")
    public ResponseEntity<?> saveSong(@RequestBody AddSongDto addSongDto) {

        ValidationResult validationResult = songService.validateSongDto(addSongDto);
        if (validationResult.hasErrors()) {
            return ResponseEntity.badRequest().body(validationResult.getErrors());
        }
        SongDto song = songService.saveSong(addSongDto);
        return ResponseEntity.status(HttpStatus.OK).body(song);
    }

    @GetMapping("/get-all-song")
    public ResponseEntity<Page<SongDto>> getAllSong(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("title").descending());
        Page<SongDto> songsPage = songService.getPageAllSongs(pageable);
        return ResponseEntity.ok(songsPage);
    }

    @GetMapping("/all-songs-by-artist")
    public ResponseEntity<Page<SongDto>> getAllSongByArtist(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam("artistId") UUID artistId) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("title").descending());
        Page<SongDto> songsPage = songService.getPageAllSongsByArtistId(pageable, artistId);
        return ResponseEntity.ok(songsPage);
    }

    @GetMapping("/search-by-title")
    public ResponseEntity<Page<SongDto>> getSongByTitle(@RequestParam("title") String title,
        @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("title").descending());
        Page<SongDto> songsPage = songService.getPageSongsByQuery(title, pageable);
        return ResponseEntity.ok(songsPage);
    }
}
