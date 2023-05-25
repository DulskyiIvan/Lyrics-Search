package edu.geekhub.example.service.song.repository;

import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.song.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {

    @Query("SELECT s FROM Song s JOIN SongView sv ON s.id = sv.song.id WHERE sv.viewedDate >= :currentDate  AND s.genre IN (:genres) GROUP BY s ORDER BY COUNT(sv) DESC")
    Page<Song> getTopSongsByViewsAndYearAndGenre(@Param("currentDate") LocalDate currentDate,
        @Param("genres") List<Genre> genres, Pageable pageable);

    @Query("SELECT s FROM Song s JOIN SongView sv ON s.id = sv.song.id WHERE (EXTRACT(YEAR FROM sv.viewedDate) > EXTRACT(YEAR FROM CAST(:startDate AS date))) OR (EXTRACT(YEAR FROM sv.viewedDate) = EXTRACT(YEAR FROM CAST(:startDate AS date)) AND EXTRACT(MONTH FROM sv.viewedDate) >= EXTRACT(MONTH FROM CAST(:startDate AS date))) AND s.genre IN (:genres) GROUP BY s ORDER BY COUNT(sv) DESC")
    Page<Song> getTopSongsByViewsAndMonth(@Param("startDate") LocalDate startDate,
        @Param("genres") List<Genre> genres, Pageable pageable);

    @Query("SELECT s FROM Song s JOIN SongView sv ON s.id = sv.song.id WHERE (EXTRACT(YEAR FROM sv.viewedDate) > EXTRACT(YEAR FROM CAST(:startDate AS date))) OR (EXTRACT(YEAR FROM sv.viewedDate) = EXTRACT(YEAR FROM CAST(:startDate AS date)) AND EXTRACT(WEEK FROM sv.viewedDate) >= EXTRACT(WEEK FROM CAST(:startDate AS date))) AND s.genre IN (:genres) GROUP BY s ORDER BY COUNT(sv) DESC")
    Page<Song> getTopSongsByViewsAndWeek(@Param("startDate") LocalDate startDate,
        @Param("genres") List<Genre> genres, Pageable pageable);

    @Query("SELECT s FROM Song s JOIN SongView sv ON s.id = sv.song.id WHERE sv.viewedDate >= :startDate AND s.genre IN (:genres) GROUP BY s ORDER BY COUNT(sv) DESC")
    Page<Song> getTopSongsByViewsAndDay(@Param("startDate") LocalDate startDate,
        @Param("genres") List<Genre> genres, Pageable pageable);

    @Query("SELECT COUNT(sv) FROM SongView sv WHERE sv.song.id = :songId")
    Long countViewsBySongId(@Param("songId") UUID songId);

    @Query("SELECT s FROM Song s WHERE (LENGTH(:title) > 0 AND (REPLACE(TRIM(LOWER(s.title)), ' ', '') ILIKE CONCAT('%', REPLACE(TRIM(LOWER(:title)), ' ', ''), '%') OR REPLACE(TRIM(LOWER(s.artist.name)), ' ', '') ILIKE CONCAT('%', REPLACE(TRIM(LOWER(:title)), ' ', ''), '%'))) ORDER BY CASE WHEN REPLACE(TRIM(LOWER(s.title)), ' ', '') ILIKE CONCAT('%', REPLACE(TRIM(LOWER(:title)), ' ', ''), '%') THEN 1 ELSE 2 END, s.title")
    List<Song> searchByTitleAndArtistName(@Param("title") String title);

    List<Song> findAllByGenre(Genre genre);

    List<Song> findAllByArtistId(UUID id);

    Page<Song> findAllByArtistId(Pageable pageable, UUID id);

    Page<Song> findAll(Pageable pageable);

    Page<Song> findAllByTitleContainingIgnoreCaseOrArtistNameContainingIgnoreCase(String title,
        String artistName, Pageable pageable);
}
