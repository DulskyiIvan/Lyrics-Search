package edu.geekhub.example.service.proposeEditLyric.service;

import edu.geekhub.example.TestApplication;
import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.repository.UserRepository;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyric;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyricDto;
import edu.geekhub.example.service.proposeEditLyric.repository.ProposeEditLyricRepository;
import edu.geekhub.example.service.role.model.Role;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DataJpaTest
@ContextConfiguration(classes = {ProposeEditLyricService.class, TestApplication.class,
    ProposeEditLyricMapper.class})
class ProposeEditLyricServiceTest {

    @Autowired
    private ProposeEditLyricService proposeEditLyricService;
    @Autowired
    private ProposeEditLyricRepository proposeEditLyricRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProposeEditLyricMapper mapper;
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
        song.setArtist(new Artist().setName("ArtistName"));
        this.song = song;
    }

    @Test
    void testAddPropose() {
        ProposeEditLyric proposeEditLyric = new ProposeEditLyric();
        proposeEditLyric.setLyricText("Lyric Text");
        proposeEditLyric.setUser(user);
        proposeEditLyric.setSong(song);
        proposeEditLyric.setCreatedAt(LocalDate.now());

        when(songService.getSongById(song.getId(), user)).thenReturn(song);

        ProposeEditLyricDto savedPropose = proposeEditLyricService.addPropose(user,
            proposeEditLyric,
            song.getId());

        assertNotNull(savedPropose.getId());
        assertEquals(savedPropose.getLyricText(), savedPropose.getLyricText());
        assertEquals(song.getTitle(), savedPropose.getSongTitle());
        assertEquals(LocalDate.now(), savedPropose.getCreatedAt());

        ProposeEditLyricDto actualPropose = mapper.apply(
            Objects.requireNonNull(
                proposeEditLyricRepository.findById(savedPropose.getId()).orElse(null)));
        assertNotNull(actualPropose);
        assertEquals(savedPropose, actualPropose);
    }
}