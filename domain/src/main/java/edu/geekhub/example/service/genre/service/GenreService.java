package edu.geekhub.example.service.genre.service;

import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.genre.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteGenre(Integer genreId) {
        genreRepository.findById(genreId)
            .ifPresentOrElse(genre -> genreRepository.deleteById(genreId),
                () -> {
                    throw new NotFoundException("Genre with id " + genreId + " not found");
                });

    }

    public Genre getGenreById(Integer id) {
        return genreRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Genre is not found")
        );
    }
}
