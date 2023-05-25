package edu.geekhub.example.service.proposeEditLyric.service;

import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyric;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyricDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProposeEditLyricMapper implements Function<ProposeEditLyric, ProposeEditLyricDto> {

    @Override
    public ProposeEditLyricDto apply(ProposeEditLyric proposeEditLyric) {
        ProposeEditLyricDto proposeEditDto = new ProposeEditLyricDto();
        proposeEditDto.setId(proposeEditLyric.getId());
        proposeEditDto.setLyricText(proposeEditLyric.getLyricText());
        proposeEditDto.setCreatedAt(proposeEditLyric.getCreatedAt());
        proposeEditDto.setSongId(proposeEditLyric.getSong().getId().toString());
        proposeEditDto.setSongTitle(proposeEditLyric.getSong().getTitle());
        proposeEditDto.setArtistName(proposeEditLyric.getSong().getArtist().getName());
        return proposeEditDto;
    }
}
