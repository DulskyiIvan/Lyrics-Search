package edu.geekhub.example.service.artist.repository;

import edu.geekhub.example.service.artist.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, UUID> {

    List<Artist> findArtistsByNameContainingIgnoreCase(String name);

    Page<Artist> findArtistsByNameContainingIgnoreCase(Pageable pageable, String name);

    Artist findArtistByNameContainingIgnoreCase(String name);

    Artist findArtistById(UUID artistId);

    Page<Artist> findAll(Pageable pageable);

    boolean existsArtistByNameContainingIgnoreCase(String name);
}
