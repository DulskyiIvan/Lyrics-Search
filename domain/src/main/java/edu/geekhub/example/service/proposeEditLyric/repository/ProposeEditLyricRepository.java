package edu.geekhub.example.service.proposeEditLyric.repository;

import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProposeEditLyricRepository extends JpaRepository<ProposeEditLyric, UUID> {

    Page<ProposeEditLyric> findAll(Pageable pageable);

    Page<ProposeEditLyric> findAllByOrderByCreatedAt(Pageable pageable);

    Page<ProposeEditLyric> findAllBySongTitle(Pageable pageable, String songTitle);

    void deleteBySongId(UUID songId);

    Optional<ProposeEditLyric> findBySongId(UUID songId);
}
