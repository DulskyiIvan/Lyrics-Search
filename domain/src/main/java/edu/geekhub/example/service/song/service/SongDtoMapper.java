package edu.geekhub.example.service.song.service;

import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.model.SongDto;
import edu.geekhub.example.service.song.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SongDtoMapper implements Function<Song, SongDto> {

    private final SongRepository songRepository;

    public SongDtoMapper(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public SongDto apply(Song song) {
        SongDto songDto = new SongDto();
        songDto.setId(song.getId());
        songDto.setArtist(song.getArtist().getName());
        songDto.setTitle(song.getTitle());
        songDto.setReleaseDate(song.getReleaseDate());
        songDto.setYoutubeLink(song.getYoutubeLink());
        songDto.setTotalViews(songRepository.countViewsBySongId(song.getId()));
        return songDto;
    }
}
