package edu.geekhub.example.service.song.service;

import edu.geekhub.example.registration.ValidationResult;
import edu.geekhub.example.service.song.model.AddSongDto;
import org.springframework.stereotype.Component;

@Component
public class SongValidator {

    public ValidationResult validate(AddSongDto song) {
        ValidationResult validationResult = new ValidationResult();
        validateTitle(song.getTitle(), validationResult);
        validateArtist(song.getArtist(), validationResult);
        validateLyric(song.getLyric(), validationResult);
        validateYoutubeLink(song.getYoutubeLink(), validationResult);
        validateGenreId(song.getGenre(), validationResult);

        return validationResult;
    }

    private void validateGenreId(Integer genre, ValidationResult validationResult) {
        if (genre == null) {
            validationResult.addError("Genre cannot be null");
        } else if (genre == 0) {
            validationResult.addError("No genre selected");
        }
    }

    private void validateYoutubeLink(String youtubeLink, ValidationResult validationResult) {
        String videoCodeRegex = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube(-nocookie)?\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$";
        if (youtubeLink != null && !youtubeLink.isBlank()) {
            if (!youtubeLink.matches(videoCodeRegex)) {
                validationResult.addError("Youtube link is not valid");
            }
        }
    }

    private void validateLyric(String lyric, ValidationResult validationResult) {
        if (lyric != null && lyric.isBlank()) {
            validationResult.addError("Lyric cannot be empty");
        } else if (lyric == null) {
            validationResult.addError("Lyric cannot be null");
        }
    }

    private void validateArtist(String artist, ValidationResult validationResult) {
        if (artist != null && artist.isBlank()) {
            validationResult.addError("Artist name cannot be empty");
        } else if (artist == null) {
            validationResult.addError("Artist name cannot be null");
        }
    }

    private void validateTitle(String title, ValidationResult validationResult) {
        if (title != null && title.isBlank()) {
            validationResult.addError("Title cannot be empty");
        } else if (title == null) {
            validationResult.addError("Title cannot be null");
        }

    }
}
