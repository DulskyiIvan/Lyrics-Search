package edu.geekhub.example.service.genre.service;

import edu.geekhub.example.TestApplication;
import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.genre.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.webjars.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {GenreService.class, TestApplication.class})
class GenreServiceTest {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreService genreService;

    @Test
    void testAddGenre() {
        Genre genre = new Genre();
        genre.setTitle("Jazz");

        Genre savedGenre = genreService.addGenre(genre);

        Genre actualGenre = genreRepository.findById(savedGenre.getId()).orElseThrow(
            () -> new NotFoundException("Genre is not found")
        );
        assertNotNull(actualGenre);
        assertEquals(savedGenre, actualGenre);
    }


    @Test
    void testGetAllGenre() {
        Genre genre = new Genre();
        genre.setTitle("Jazz");

        List<Genre> excpectedList = genreRepository.findAll();
        Genre savedGenre = genreRepository.save(genre);
        excpectedList.add(savedGenre);

        List<Genre> actualGenreList = genreService.getAll();

        assertEquals(excpectedList, actualGenreList);
    }

    @Test
    void testGetGenreById() {
        Genre genre = new Genre();
        genre.setTitle("Jazz");

        Genre savedGenre = genreRepository.save(genre);

        Genre actualGenre = genreService.getGenreById(savedGenre.getId());
        assertEquals(savedGenre, actualGenre);
    }

    @Test
    void testDeleteGenreById() {
        Genre genre = new Genre();
        genre.setTitle("Jazz");

        Genre savedGenre = genreRepository.save(genre);

        genreService.deleteGenre(savedGenre.getId());
        assertFalse(genreService.getAll().contains(savedGenre));
    }
}