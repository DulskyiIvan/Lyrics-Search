package edu.geekhub.example.service.proposeEditLyric.service;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyric;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyricDto;
import edu.geekhub.example.service.proposeEditLyric.repository.ProposeEditLyricRepository;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ProposeEditLyricService {

    private final ProposeEditLyricRepository proposeEditLyricRepository;
    private final SongService songService;
    private final ProposeEditLyricMapper mapper;

    public ProposeEditLyricService(ProposeEditLyricRepository proposeEditLyricRepository,
        SongService songService, ProposeEditLyricMapper mapper) {
        this.proposeEditLyricRepository = proposeEditLyricRepository;
        this.songService = songService;
        this.mapper = mapper;
    }

    public ProposeEditLyricDto addPropose(User user, ProposeEditLyric proposeEdit, UUID songId) {
        proposeEdit.setUser(user);
        Song song = songService.getSongById(songId, user);
        proposeEdit.setSong(song);
        proposeEdit.setCreatedAt(LocalDate.now());
        return mapper.apply(proposeEditLyricRepository.save(proposeEdit));
    }

    public Page<ProposeEditLyricDto> getAllByOrderBySongTitle(Pageable pageable) {
        return proposeEditLyricRepository.findAll(pageable)
            .map(mapper);
    }

    public Page<ProposeEditLyricDto> getAllByCreatedAt(Pageable pageable) {
        return proposeEditLyricRepository.findAllByOrderByCreatedAt(pageable)
            .map(mapper);
    }

    public void deletePropose(UUID proposeId) {
        proposeEditLyricRepository.findById(proposeId)
            .ifPresentOrElse(
                proposeEditLyric -> proposeEditLyricRepository.deleteById(proposeId),
                () -> {
                    throw new NotFoundException("Propose edit with id " + proposeId + " not found");
                });
    }

    public void deleteProposeBySongId(UUID songId) {
        proposeEditLyricRepository.findBySongId(songId)
            .ifPresentOrElse(
                proposeEditLyric -> proposeEditLyricRepository.deleteBySongId(songId),
                () -> {
                    throw new NotFoundException(
                        "Propose edit with song id" + songId + " not found");
                });
    }
}
