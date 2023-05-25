package edu.geekhub.example.service.song.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.geekhub.example.TestApplication;
import edu.geekhub.example.service.MockConfig;
import edu.geekhub.example.authentication.config.WebSecurityConfig;
import edu.geekhub.example.registration.ValidationResult;
import edu.geekhub.example.service.song.model.AddSongDto;
import edu.geekhub.example.service.song.model.SongDto;
import edu.geekhub.example.service.song.service.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SongRestController.class)
@ContextConfiguration(classes = {WebSecurityConfig.class, TestApplication.class, MockConfig.class})
class SongRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SongService songService;


    @Test
    @WithUserDetails("username")
    void testSaveSong() throws Exception {
        ValidationResult validationResult = new ValidationResult();

        AddSongDto addSongDto = new AddSongDto();
        addSongDto.setTitle("Song Title");
        addSongDto.setArtist("Artist Name");
        addSongDto.setLyric("Song Lyric");
        addSongDto.setGenre(1);

        SongDto songDto = new SongDto();
        songDto.setId(UUID.randomUUID());
        songDto.setTitle("Song Title");
        songDto.setArtist("Artist Name");

        when(songService.validateSongDto(any(AddSongDto.class))).thenReturn(validationResult);
        when(songService.saveSong(addSongDto)).thenReturn(songDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/save-song")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSongDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testSaveSongWithValidationError() throws Exception {
        ValidationResult validationResult = new ValidationResult();

        validationResult.addError("Lyric cannot be null");

        SongDto songDto = new SongDto();
        songDto.setId(UUID.randomUUID());
        songDto.setTitle("Song Title");
        songDto.setArtist("Artist Name");

        when(songService.validateSongDto(any(AddSongDto.class))).thenReturn(validationResult);

        mockMvc.perform(MockMvcRequestBuilders.post("/save-song")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(songDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldThrowFound() throws Exception {
        mockMvc.perform(get("/get-all-song"))
                .andExpect(status()
                        .isFound());
    }

    @Test
    @WithUserDetails("username")
    void testGetAllSong() throws Exception {
        SongDto songDto = new SongDto();
        songDto.setId(UUID.randomUUID());
        songDto.setTitle("Song Title");
        songDto.setArtist("Artist Name");

        Page<SongDto> songPage = new PageImpl<>(List.of(songDto));

        doReturn(songPage).when(songService).getPageAllSongs(any(Pageable.class));

        mockMvc.perform(get("/get-all-song").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(songPage)))
                .andExpect(status().isOk());

        verify(songService, times(1)).getPageAllSongs(any(Pageable.class));

    }

    @Test
    @WithUserDetails("username")
    void testGetSongByTitle() throws Exception {
        SongDto songDto = new SongDto();
        songDto.setId(UUID.randomUUID());
        songDto.setTitle("Song Title");
        songDto.setArtist("Artist Name");

        Page<SongDto> songPage = new PageImpl<>(List.of(songDto));

        when(songService.getPageSongsByQuery(eq("SongTitle"), any(Pageable.class))).thenReturn(
                songPage);

        mockMvc.perform(
                        get("/search-by-title?title=" + "SongTitle").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(songPage)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testGetSearchResult() throws Exception {
        SongDto songDto = new SongDto();
        songDto.setId(UUID.randomUUID());
        songDto.setTitle("Song Title");
        songDto.setArtist("Artist Name");

        when(songService.getSongsByQuery("SongTitle")).thenReturn(
                List.of(songDto));

        mockMvc.perform(
                        get("/search-result?query=" + "SongTitle").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(songDto))))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testGetAllSongByArtist() throws Exception {
        SongDto songDto = new SongDto();
        songDto.setId(UUID.randomUUID());
        songDto.setTitle("Song Title");
        songDto.setArtist("Artist Name");

        UUID artistId = UUID.fromString("ccfd885a-f315-11ed-a05b-0242ac120003");

        Page<SongDto> songPage = new PageImpl<>(List.of(songDto));

        when(songService.getPageAllSongsByArtistId(any(Pageable.class),
                eq(artistId))).thenReturn(
                songPage);

        mockMvc.perform(
                        get("/all-songs-by-artist?artistId=" + artistId).accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(songPage)))
                .andExpect(status().isOk());
    }


}