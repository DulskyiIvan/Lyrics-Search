package edu.geekhub.example.service.lyric.service;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.lyric.model.Lyric;
import edu.geekhub.example.service.lyric.model.LyricDto;
import edu.geekhub.example.service.lyric.repository.LyricRepository;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.service.SongService;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class LyricService {

    private final LyricRepository lyricRepository;
    private final SongService songService;
    private final LyricDtoMapper mapper;


    public LyricService(LyricRepository lyricRepository, SongService songService,
        LyricDtoMapper mapper) {
        this.lyricRepository = lyricRepository;
        this.songService = songService;
        this.mapper = mapper;
    }

    public void deleteAllLyricBySongId(UUID songId) {
        lyricRepository.deleteLyricsBySongId(songId);
    }

    public LyricDto addLyric(User user, Lyric lyric, UUID songId) {

        lyric.setUser(user);
        Song song = songService.getSongById(songId, lyric.getUser());
        lyric.setCreatedAt(LocalDate.now());
        lyric.setSong(song);
        return mapper.apply(lyricRepository.save(lyric));
    }


    public void deleteLyric(UUID songId, UUID lyricId) {
        List<Lyric> lyrics = lyricRepository.findAllBySongId(songId);
        if (lyrics.size() == 1) {
            throw new RuntimeException("Cannot delete the last record in the database");
        }
        lyricRepository.deleteById(lyricId);
    }

    public String getLyricTextFromDoc(MultipartFile file) {
        String documentContent;
        try (InputStream is = file.getInputStream()) {
            XWPFDocument document = new XWPFDocument(is);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            documentContent = extractor.getText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return documentContent;
    }

    public String getLyricTextFromTxt(MultipartFile file) {
        try {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteArrayResource getByteResource(UUID songId, String lyric) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Song song = songService.getSongById(songId, user);

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph artistParagraph = document.createParagraph();
        XWPFRun artistRun = artistParagraph.createRun();
        artistRun.setBold(true);
        artistRun.setFontSize(16);
        artistRun.setText(song.getArtist().getName());
        artistRun.addBreak();

        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setFontSize(14);
        titleRun.setText(song.getTitle());
        titleRun.addBreak();

        XWPFParagraph songParagraph = document.createParagraph();
        XWPFRun songRun = songParagraph.createRun();
        songRun.setFontSize(12);
        songRun.setFontFamily("Times New Roman");

        if (lyric.contains("\n")) {
            String[] lines = lyric.split("\n");
            songRun.setText(lines[0], 0);
            for (int i = 1; i < lines.length; i++) {
                songRun.addBreak();
                songRun.setText(lines[i]);
            }
        } else {
            songRun.setText(lyric, 0);
        }

        document.write(out);
        out.close();
        byte[] bytes = out.toByteArray();
        return new ByteArrayResource(bytes, song.getTitle() + ".docx");
    }

}
