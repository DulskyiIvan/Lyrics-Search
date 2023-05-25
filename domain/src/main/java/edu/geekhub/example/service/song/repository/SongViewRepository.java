package edu.geekhub.example.service.song.repository;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.model.SongView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface SongViewRepository extends JpaRepository<SongView, Long> {

    boolean existsSongViewBySongAndUser(Song song, User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM SongView v WHERE v.song.id = :songId")
    void deleteAllBySongId(@Param("songId") UUID songId);
}
