package edu.geekhub.example.service.lyric.controller;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.lyric.model.Lyric;
import edu.geekhub.example.service.lyric.model.LyricDto;
import edu.geekhub.example.service.lyric.service.LyricService;
import edu.geekhub.example.service.song.service.SongService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/lyric")
public class LyricController {

    private final LyricService lyricService;
    private final SongService songService;

    public LyricController(LyricService lyricService, SongService songService) {
        this.lyricService = lyricService;
        this.songService = songService;
    }

    @PostMapping("/add")
    public ResponseEntity<LyricDto> addLyric(@RequestBody Lyric lyric,
        @RequestParam("songId") UUID songId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (lyric.getText() != null || !lyric.getText().isBlank()) {
            LyricDto addedLyric = lyricService.addLyric(user, lyric, songId);
            if (addedLyric != null) {
                return ResponseEntity.ok(addedLyric);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/download-lyric")
    public ResponseEntity<ByteArrayResource> downloadSongText(@RequestParam("songId") UUID songId,
        @RequestParam("lyric") String lyric) throws IOException {

        return ResponseEntity.ok()
            .body(lyricService.getByteResource(songId, lyric));
    }

    @PostMapping("/lyric-from-file")
    public ResponseEntity<String> getLyricFromFile(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        if ("text/plain".equals(contentType)) {
            String text = lyricService.getLyricTextFromTxt(file);
            return ResponseEntity.ok(text);
        } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(
            contentType)) {
            String text = lyricService.getLyricTextFromDoc(file);
            return ResponseEntity.ok(text);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteLyric(@RequestBody Lyric lyric,
        @RequestParam("songId") UUID songId) {
        lyricService.deleteLyric(songId, lyric.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
