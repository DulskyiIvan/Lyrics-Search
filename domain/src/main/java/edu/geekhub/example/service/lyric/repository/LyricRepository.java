package edu.geekhub.example.service.lyric.repository;

import edu.geekhub.example.service.lyric.model.Lyric;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LyricRepository extends JpaRepository<Lyric, UUID> {

    Lyric findLyricBySongIdOrderByCreatedAtDesc(UUID songId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Lyric l WHERE l.song.id = :songId")
    void deleteLyricsBySongId(@Param("songId") UUID songId);

    List<Lyric> findAllBySongId(UUID songId);
}
