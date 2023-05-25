package edu.geekhub.example.service.genre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.geekhub.example.TestApplication;
import edu.geekhub.example.service.MockConfig;
import edu.geekhub.example.authentication.config.WebSecurityConfig;
import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.genre.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@ContextConfiguration(classes = {TestApplication.class, WebSecurityConfig.class, MockConfig.class})
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;

    @Test
    @WithUserDetails("username")
    void testGetAllGenres() throws Exception {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setTitle("Rock");

        when(genreService.getAll()).thenReturn(List.of(genre));

        mockMvc.perform(get("/genre/all").accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(genre))))
            .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("adminname")
    void testGetAllGenresAdmin() throws Exception {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setTitle("Rock");

        when(genreService.getAll()).thenReturn(List.of(genre));

        mockMvc.perform(get("/genre/all").accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(genre))))
            .andExpect(status().isOk());
    }
}