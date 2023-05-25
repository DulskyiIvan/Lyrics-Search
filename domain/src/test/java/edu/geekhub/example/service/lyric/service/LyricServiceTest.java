package edu.geekhub.example.service.lyric.service;

import edu.geekhub.example.TestApplication;
import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.lyric.model.Lyric;
import edu.geekhub.example.service.lyric.model.LyricDto;
import edu.geekhub.example.service.lyric.repository.LyricRepository;
import edu.geekhub.example.service.role.model.Role;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.service.SongService;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ContextConfiguration(classes = {LyricService.class, TestApplication.class,
    LyricDtoMapper.class})
class LyricServiceTest {

    @Autowired
    private LyricService lyricService;
    @Autowired
    private LyricDtoMapper lyricDtoMapper;
    @Autowired
    private LyricRepository lyricRepository;
    @MockBean
    private SongService songService;

    private User user;
    private Song song;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testUser");
        user.setRole(new Role().setId(1).setTitle("USER"));
        this.user = user;

        Song song = new Song();
        song.setId(UUID.randomUUID());
        song.setTitle("Test Song");
        this.song = song;
    }

    @Test
    void addLyric() {
        Lyric lyric = new Lyric();
        lyric.setUser(user);
        lyric.setSong(song);
        lyric.setText("LyricText");
        lyric.setCreatedAt(LocalDate.now());

        when(songService.getSongById(song.getId(), user)).thenReturn(song);

        LyricDto savedLyric = lyricService.addLyric(user, lyric, song.getId());

        LyricDto actualLyric = lyricRepository.findById(savedLyric.getId()).map(lyricDtoMapper)
            .orElse(null);

        assertEquals(savedLyric, actualLyric);
    }

    @Test
    void testGetLyricTextFromDoc() {
        String filePath = "files/testFile.docx";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        byte[] fileContent;

        try {
            fileContent = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test file: " + filePath, e);
        }
        MockMultipartFile file = new MockMultipartFile("test.docx", "testFile.docx",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", fileContent);

        String result = lyricService.getLyricTextFromDoc(file);

        try (InputStream is = new ByteArrayInputStream(fileContent)) {
            XWPFDocument document = new XWPFDocument(is);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String documentContent = extractor.getText();

            assertEquals(documentContent, result);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test file content", e);
        }
    }

    @Test
    void testGetLyricFromTxt() {
        String filePath = "files/testFile.docx";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        byte[] fileContent;

        try {
            fileContent = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test file: " + filePath, e);
        }
        MockMultipartFile file = new MockMultipartFile("test.txt", "testFile.txt",
            "text/plain", fileContent);

        String result = lyricService.getLyricTextFromTxt(file);
        String documentContent = new String(fileContent, StandardCharsets.UTF_8);
        assertEquals(documentContent, result);
    }

    @Test
    void testGetByteResource() throws IOException {
        UUID songId = UUID.randomUUID();
        String lyric = "Test Lyric";

        SecurityContextHolder.getContext()
            .setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

        Song song = new Song();
        song.setId(songId);
        song.setArtist(new Artist());
        song.getArtist().setName("Test Artist");
        song.setTitle("Test Song");

        when(songService.getSongById(songId, user)).thenReturn(song);

        ByteArrayResource resource = lyricService.getByteResource(songId, lyric);

        assertNotNull(resource);
        assertTrue(resource.exists());

        byte[] bytes = resource.getByteArray();
        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(bytes));

        XWPFParagraph artistParagraph = document.getParagraphArray(0);
        XWPFRun artistRun = artistParagraph.getRuns().get(0);
        assertEquals("Test Artist", artistRun.getText(0));

        XWPFParagraph titleParagraph = document.getParagraphArray(1);
        XWPFRun titleRun = titleParagraph.getRuns().get(0);
        assertEquals("Test Song", titleRun.getText(0));

        XWPFParagraph songParagraph = document.getParagraphArray(2);
        XWPFRun songRun = songParagraph.getRuns().get(0);
        assertEquals("Test Lyric", songRun.getText(0));
    }
}