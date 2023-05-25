package edu.geekhub.example.service.comments.service;

import edu.geekhub.example.TestApplication;
import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.comments.model.Comment;
import edu.geekhub.example.service.comments.model.CommentDto;
import edu.geekhub.example.service.comments.repository.CommentRepository;
import edu.geekhub.example.service.role.model.Role;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@DataJpaTest
@ContextConfiguration(classes = {CommentService.class, TestApplication.class,
    CommentDtoMapper.class})
class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentDtoMapper commentDtoMapper;
    @Autowired
    private CommentRepository commentRepository;
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
    void testSaveComment() {
        Comment comment = new Comment();
        comment.setCommentText("Test Comment");
        comment.setUser(user);
        comment.setSong(song);
        comment.setCreatedAt(LocalDate.now());

        when(songService.getSongById(song.getId(), user)).thenReturn(song);

        CommentDto savedComment = commentService.saveComment(user, comment.getCommentText(),
            song.getId());

        assertNotNull(savedComment.getId());
        assertEquals(comment.getCommentText(), savedComment.getCommentText());
        assertEquals(user.getUsername(), savedComment.getUsername());
        assertEquals(LocalDate.now(), savedComment.getCreatedAt());

        CommentDto actualComment = commentDtoMapper.apply(
            Objects.requireNonNull(commentRepository.findById(savedComment.getId()).orElse(null)));
        assertNotNull(actualComment);
        assertEquals(savedComment, actualComment);
    }

    @Test
    void testGetCommentById() {

        Comment comment = new Comment();
        comment.setCommentText("Test Comment");
        comment.setUser(user);
        comment.setSong(song);
        comment.setCreatedAt(LocalDate.now());

        when(songService.getSongById(song.getId(), user)).thenReturn(song);

        CommentDto savedComment = commentService.saveComment(user, comment.getCommentText(),
            song.getId());
        comment.setId(savedComment.getId());
        Comment actualComment = commentRepository.findById(savedComment.getId()).orElseThrow(
            () -> new NotFoundException("Comment with id " + savedComment.getId() + " not found")
        );
        assertNotNull(actualComment);
        assertEquals(comment, actualComment);
    }


}