package edu.geekhub.example.service.comments.service;

import edu.geekhub.example.service.comments.model.Comment;
import edu.geekhub.example.service.comments.model.CommentDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDtoMapper implements Function<Comment, CommentDto> {

    @Override
    public CommentDto apply(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setUsername(comment.getUser().getUsername());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }
}
