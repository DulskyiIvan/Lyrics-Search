package edu.geekhub.example.service.artist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.geekhub.example.TestApplication;
import edu.geekhub.example.service.MockConfig;
import edu.geekhub.example.authentication.config.WebSecurityConfig;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.service.ArtistService;
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

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistController.class)
@ContextConfiguration(classes = {TestApplication.class, WebSecurityConfig.class, MockConfig.class})
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArtistService artistService;

    @Test
    @WithUserDetails("username")
    void testSearchArtist() throws Exception {
        Artist artist = new Artist();
        artist.setId(UUID.randomUUID());
        artist.setName("ArtistName");
        artist.setDescription("ArtistDescription");

        when(artistService.findByNameContainingIgnoreCase("artistname")).thenReturn(
                List.of(artist));

        mockMvc.perform(
                        get("/artist/artist-search?query=artistname").accept(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(List.of(artist))))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testGetPageOfArtistsByQuery() throws Exception {
        Artist artist = new Artist();
        artist.setId(UUID.randomUUID());
        artist.setName("ArtistName");
        artist.setDescription("ArtistDescription");

        Page<Artist> artistPage = new PageImpl<>(List.of(artist));

        when(artistService.getPageOfArtistsByQuery(any(Pageable.class),
                eq("artistname"))).thenReturn(
                artistPage);

        mockMvc.perform(
                        get("/artist/artist-page?query=artistname").accept(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(artistPage)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testGetPageAllArtists() throws Exception {
        Artist artist = new Artist();
        artist.setId(UUID.randomUUID());
        artist.setName("ArtistName");
        artist.setDescription("ArtistDescription");

        Page<Artist> artistPage = new PageImpl<>(List.of(artist));

        when(artistService.getAll(any(Pageable.class))).thenReturn(
                artistPage);

        mockMvc.perform(
                        get("/artist/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(artistPage)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testGetArtistById() throws Exception {
        Artist artist = new Artist();
        artist.setId(UUID.randomUUID());
        artist.setName("ArtistName");
        artist.setDescription("ArtistDescription");

        when(artistService.getArtistById(artist.getId())).thenReturn(
                artist);

        mockMvc.perform(get("/artist/{id}", artist.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(artist)))
                .andExpect(status().isOk());
    }
}