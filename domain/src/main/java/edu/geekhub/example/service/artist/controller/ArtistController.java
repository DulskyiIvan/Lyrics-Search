package edu.geekhub.example.service.artist.controller;

import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.service.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist-search")
    public ResponseEntity<List<Artist>> searchArtist(@RequestParam("query") String query) {
        return ResponseEntity.ok(artistService.findByNameContainingIgnoreCase(query));
    }

    @GetMapping("/artist-page")
    public ResponseEntity<Page<Artist>> getPageOfArtistsByQuery(@RequestParam("query") String query, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Artist> artistsPage = artistService.getPageOfArtistsByQuery(pageable, query);
        return ResponseEntity.ok(artistsPage);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Artist>> getPageAllArtists(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Artist> pageArtists = artistService.getAll(pageable);
        return ResponseEntity.ok(pageArtists);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable(value = "id") UUID artistId) {
        return ResponseEntity.ok(artistService.getArtistById(artistId));
    }
}
