package edu.geekhub.example.service.lyric.service;

import edu.geekhub.example.service.lyric.model.Lyric;
import edu.geekhub.example.service.lyric.model.LyricDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LyricDtoMapper implements Function<Lyric, LyricDto> {

    @Override
    public LyricDto apply(Lyric lyric) {
        LyricDto lyricDto = new LyricDto();
        lyricDto.setId(lyric.getId());
        lyricDto.setText(lyric.getText());
        lyricDto.setCreatedAt(lyric.getCreatedAt());
        lyricDto.setSongId(lyric.getSong().getId());
        lyricDto.setUserId(lyric.getUser().getId());
        return lyricDto;
    }
}
