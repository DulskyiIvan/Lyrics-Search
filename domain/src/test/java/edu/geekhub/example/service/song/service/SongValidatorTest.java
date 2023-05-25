package edu.geekhub.example.service.song.service;

import edu.geekhub.example.registration.ValidationResult;
import edu.geekhub.example.service.song.model.AddSongDto;
import edu.geekhub.example.service.song.service.SongValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SongValidatorTest {

    private final SongValidator songValidator = new SongValidator();

    private final AddSongDto validAddSongDto = new AddSongDto();
    private final AddSongDto inValidAddSongDto = new AddSongDto();

    @Test
    void testValidateValidTitleAddSongDto() {

        validAddSongDto.setTitle("Was it a Dream");

        ValidationResult validationResult = songValidator.validate(validAddSongDto);

        assertFalse(validationResult.getErrors().contains("Title cannot be empty"));
        assertFalse(validationResult.getErrors().contains("Title cannot be null"));
    }

    @Test
    void testValidateValidArtistAddSongDto() {
        validAddSongDto.setArtist("30 Seconds To Mars");

        ValidationResult validationResult = songValidator.validate(validAddSongDto);

        assertFalse(validationResult.getErrors().contains("Artist name cannot be empty"));
        assertFalse(validationResult.getErrors().contains("Artist name cannot be null"));
    }

    @Test
    void testValidateValidLyricAddSongDto() {

        validAddSongDto.setLyric("Lyric text");
        ValidationResult validationResult = songValidator.validate(validAddSongDto);

        assertFalse(validationResult.getErrors().contains("Lyric cannot be empty"));
        assertFalse(validationResult.getErrors().contains("Lyric cannot be null"));
    }

    @Test
    void testValidateValidYoutubeLinkAddSongDto() {

        validAddSongDto.setYoutubeLink("https://www.youtube.com/watch?v=bImSPwq6FFE");

        ValidationResult validationResult = songValidator.validate(validAddSongDto);

        assertFalse(validationResult.getErrors().contains("Youtube link is not valid"));
    }

    @Test
    void testValidateValidGenreAddSongDto() {
        validAddSongDto.setGenre(1);

        ValidationResult validationResult = songValidator.validate(validAddSongDto);

        assertFalse(validationResult.getErrors().contains("Genre cannot be null"));
        assertFalse(validationResult.getErrors().contains("No genre selected"));
    }

    @Test
    void testValidateInValidAddSongDto() {

        inValidAddSongDto.setTitle("");
        inValidAddSongDto.setArtist("30 Seconds To Mars");
        inValidAddSongDto.setYoutubeLink("com.youtube");
        inValidAddSongDto.setGenre(0);

        ValidationResult validationResult = songValidator.validate(validAddSongDto);

        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testValidateEmptyTitleAddSongDto() {

        inValidAddSongDto.setTitle("");

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Title cannot be empty"));
    }

    @Test
    void testValidateNullTitleAddSongDto() {

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Title cannot be null"));
    }

    @Test
    void testValidateEmptyArtistAddSongDto() {
        inValidAddSongDto.setArtist("");

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Artist name cannot be empty"));

    }

    @Test
    void testValidateNullArtistAddSongDto() {

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Artist name cannot be null"));
    }

    @Test
    void testValidateEmptyLyricAddSongDto() {

        inValidAddSongDto.setLyric("");
        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Lyric cannot be empty"));
        assertFalse(validationResult.getErrors().contains("Lyric cannot be null"));
    }

    @Test
    void testValidateNullLyricAddSongDto() {

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Lyric cannot be null"));
    }

    @Test
    void testValidateInvalidYoutubeLinkAddSongDto() {

        inValidAddSongDto.setYoutubeLink("com.youtube");

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Youtube link is not valid"));
    }

    @Test
    void testValidateNullGenreAddSongDto() {

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("Genre cannot be null"));
    }

    @Test
    void testValidateInvalidGenreAddSongDto() {
        inValidAddSongDto.setGenre(0);

        ValidationResult validationResult = songValidator.validate(inValidAddSongDto);

        assertTrue(validationResult.getErrors().contains("No genre selected"));
    }
}