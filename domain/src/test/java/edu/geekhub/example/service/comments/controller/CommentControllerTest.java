package edu.geekhub.example.service.comments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.geekhub.example.TestApplication;
import edu.geekhub.example.service.MockConfig;
import edu.geekhub.example.authentication.config.WebSecurityConfig;
import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.comments.model.CommentDto;
import edu.geekhub.example.service.comments.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@ContextConfiguration(classes = {TestApplication.class, WebSecurityConfig.class, MockConfig.class})
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    @WithUserDetails("username")
    void testGetCommentsBySongId() throws Exception {
        UUID songId = UUID.randomUUID();

        CommentDto commentDto = new CommentDto();
        commentDto.setId(UUID.randomUUID());
        commentDto.setCommentText("CommentText");
        commentDto.setUsername("username");

        when(commentService.getAllCommentsOfSong(songId)).thenReturn(List.of(commentDto));

        mockMvc.perform(
                get("/comments/get-comments?songId=" + songId).accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(commentDto))))
            .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testAddComment() throws Exception {
        UUID songId = UUID.randomUUID();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentDto commentDto = new CommentDto();
        commentDto.setId(UUID.randomUUID());
        commentDto.setCommentText("SomethingComments");
        commentDto.setUsername("username");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("commentText", "SomethingComments");

        when(commentService.saveComment(user, "SomethingComments", songId)).thenReturn(commentDto);

        mockMvc.perform(
                post("/comments/add/{songId}", songId).accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestBody))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(commentDto)))
            .andExpect(status().isOk());

        verify(commentService, times(1)).saveComment(user, "SomethingComments", songId);
    }

    @Test
    @WithUserDetails("username")
    void testDeleteComment() throws Exception {
        UUID commentId = UUID.randomUUID();

        mockMvc.perform(
                delete("/comments/delete?id=" + commentId).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(commentService, times(1))
            .deleteComment(commentId);
    }
}