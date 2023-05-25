package edu.geekhub.example.service.artist.service;

import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.model.ArtistDto;
import edu.geekhub.example.service.artist.repository.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Page<Artist> getAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }


    public List<Artist> findByNameContainingIgnoreCase(String query) {
        if (query.isBlank()) {
            return Collections.emptyList();
        }
        return artistRepository.findArtistsByNameContainingIgnoreCase(query);
    }

    public Page<Artist> getPageOfArtistsByQuery(Pageable pageable, String query) {
        if (query.isBlank()) {
            return Page.empty();
        }
        return artistRepository.findArtistsByNameContainingIgnoreCase(pageable, query);
    }

    public Artist getArtistByName(String name) {
        return artistRepository.findArtistByNameContainingIgnoreCase(name);
    }

    public boolean existsArtistByName(String name) {
        return artistRepository.existsArtistByNameContainingIgnoreCase(name);
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public ArtistDto updateArtist(Artist artist) {
        return new ArtistDto(artistRepository.save(artist));
    }

    public Artist getArtistById(UUID artistId) {
        return artistRepository.findById(artistId)
            .orElseThrow(
                () -> new NotFoundException("Artist with id " + artistId + " not found")
            );
    }

    public void deleteArtist(UUID id) {
        artistRepository.findById(id)
            .ifPresentOrElse(artist -> artistRepository.deleteById(id),
                () -> {
                    throw new NotFoundException("Artist with id " + id + " not found");
                });
    }
}
