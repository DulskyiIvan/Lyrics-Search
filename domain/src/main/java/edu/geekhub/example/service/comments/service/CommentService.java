package edu.geekhub.example.service.comments.service;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.comments.model.Comment;
import edu.geekhub.example.service.comments.model.CommentDto;
import edu.geekhub.example.service.comments.repository.CommentRepository;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.service.SongService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDtoMapper mapper;
    private final SongService songService;

    public CommentService(CommentRepository commentRepository, CommentDtoMapper mapper,
        SongService songService) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.songService = songService;
    }

    public CommentDto saveComment(User user, String commentText, UUID songId) {
        Song song = songService.getSongById(songId, user);

        Comment comment = new Comment();

        comment.setCommentText(commentText);
        comment.setUser(user);
        comment.setSong(song);
        comment.setCreatedAt(LocalDate.now());
        Comment savedComment = commentRepository.save(comment);
        return mapper.apply(savedComment);
    }

    public List<CommentDto> getAllCommentsOfSong(UUID songId) {
        return commentRepository.findAllBySongId(songId)
            .stream()
            .map(mapper)
            .collect(Collectors.toList());
    }

    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id)
            .orElseThrow(
                () -> new NotFoundException("Comment with id " + id + " not found")
            );
    }

    public void deleteComment(UUID commentId) {
        commentRepository.delete(getCommentById(commentId));
    }

    public void deleteAllCommentsBySongId(UUID songId) {
        commentRepository.deleteAllBySongId(songId);
    }
}
