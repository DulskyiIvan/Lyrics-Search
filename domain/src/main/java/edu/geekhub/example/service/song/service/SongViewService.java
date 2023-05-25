package edu.geekhub.example.service.song.service;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.model.SongView;
import edu.geekhub.example.service.song.repository.SongViewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class SongViewService {

    private final SongViewRepository songViewRepository;

    public SongViewService(SongViewRepository songViewRepository) {
        this.songViewRepository = songViewRepository;
    }

    public void addSongView(Song song, User user) {
        if (!songViewRepository.existsSongViewBySongAndUser(song, user)) {
            SongView songView = new SongView();
            songView.setSong(song);
            songView.setUser(user);
            songView.setViewedDate(LocalDate.now());
            songViewRepository.save(songView);
        }
    }

    public void deleteViewsBySongId(UUID songId) {
        songViewRepository.deleteAllBySongId(songId);
    }
}
