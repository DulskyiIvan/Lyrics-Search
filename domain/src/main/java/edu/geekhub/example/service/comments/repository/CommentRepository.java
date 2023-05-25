package edu.geekhub.example.service.comments.repository;

import edu.geekhub.example.service.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllBySongId(UUID songId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.song.id = :songId")
    void deleteAllBySongId(@Param("songId") UUID songId);
}
