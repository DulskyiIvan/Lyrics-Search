package edu.geekhub.example.service.artist.service;

import edu.geekhub.example.TestApplication;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {ArtistService.class, TestApplication.class})
class ArtistServiceTest {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistService artistService;
    private Artist artist;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setName("ArtistName");
        artist.setDescription("ArtistDesc");
        this.artist = artist;
    }

    @Test
    void testGetAll() {
        Pageable pageable = PageRequest.of(0, 1);

        Artist savedArtist = artistRepository.save(artist);
        artist.setId(savedArtist.getId());

        Page<Artist> expectedPage = new PageImpl<>(List.of(artist));
        Page<Artist> actualPage = artistService.getAll(pageable);
        assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());

        assertEquals(expectedPage.getNumberOfElements(), actualPage.getNumberOfElements());
        assertEquals(expectedPage.getNumber(), actualPage.getNumber());
        assertEquals(expectedPage.getTotalPages(), actualPage.getTotalPages());
        assertEquals(expectedPage.getSize(), actualPage.getSize());
        assertEquals(expectedPage.getContent(), actualPage.getContent());

    }

    @Test
    void testFindByNameContainingIgnoreCaseWithBlankQuery() {
        List<Artist> expectedList = Collections.emptyList();

        assertEquals(expectedList, artistService.findByNameContainingIgnoreCase(""));
    }

    @Test
    void testFindByNameContainingIgnoreCase() {

        Artist savedArtist = artistRepository.save(artist);
        artist.setId(savedArtist.getId());

        List<Artist> expectedList = List.of(artist);

        assertEquals(expectedList, artistService.findByNameContainingIgnoreCase("artistname"));
    }

    @Test
    void testGetPageOfArtistsByQuery() {
        Pageable pageable = PageRequest.of(0, 1);

        Artist savedArtist = artistRepository.save(artist);
        artist.setId(savedArtist.getId());

        Page<Artist> expectedPage = new PageImpl<>(List.of(artist));
        Page<Artist> actualPage = artistService.getPageOfArtistsByQuery(pageable, "ArtistName");
        assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());

        assertEquals(expectedPage.getNumberOfElements(), actualPage.getNumberOfElements());
        assertEquals(expectedPage.getNumber(), actualPage.getNumber());
        assertEquals(expectedPage.getTotalPages(), actualPage.getTotalPages());
        assertEquals(expectedPage.getSize(), actualPage.getSize());
        assertEquals(expectedPage.getContent(), actualPage.getContent());
    }

    @Test
    void testGetArtistByName() {

        Artist savedArtist = artistRepository.save(artist);
        artist.setId(savedArtist.getId());

        assertEquals(artist, artistService.getArtistByName("artis"));
    }

    @Test
    void testExistsArtistByName() {

        artistRepository.save(artist);

        assertTrue(artistService.existsArtistByName("ArtistName"));
    }

    @Test
    void testSave() {

        Artist savedArtist = artistService.save(artist);

        assertTrue(artistRepository.findAll().contains(savedArtist));
    }

    @Test
    void testGetArtistById() {

        Artist savedArtist = artistRepository.save(artist);
        artist.setId(savedArtist.getId());

        assertEquals(artist, artistService.getArtistById(savedArtist.getId()));
    }

    @Test
    void testDeleteArtist() {

        Artist savedArtist = artistRepository.save(artist);
        artist.setId(savedArtist.getId());

        artistService.deleteArtist(savedArtist.getId());

        assertFalse(artistRepository.findAll().contains(savedArtist));
    }

    @Test
    void testUpdateArtist() {
        Artist savedArtist = artistRepository.save(artist);
        artist.setId(savedArtist.getId());

        Artist updatedArtist = new Artist();
        updatedArtist.setId(savedArtist.getId());
        updatedArtist.setName("NewArtistName");
        updatedArtist.setDescription("NewArtistDesc");

        artistService.updateArtist(updatedArtist);

        assertEquals(updatedArtist, artistRepository.findArtistById(updatedArtist.getId()));
    }
}