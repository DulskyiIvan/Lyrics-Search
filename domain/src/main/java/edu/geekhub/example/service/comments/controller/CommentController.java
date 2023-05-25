package edu.geekhub.example.service.comments.controller;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.comments.model.CommentDto;
import edu.geekhub.example.service.comments.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/get-comments")
    public ResponseEntity<List<CommentDto>> getCommentsBySongId(
        @RequestParam("songId") UUID songId) {
        return ResponseEntity.ok(commentService.getAllCommentsOfSong(songId));
    }

    @PostMapping("/add/{songId}")
    public ResponseEntity<CommentDto> addComment(@PathVariable UUID songId,
        @RequestBody Map<String, String> requestBody) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
            commentService.saveComment(user,requestBody.get("commentText"), songId));
    }

    @DeleteMapping("/delete")
    public void deleteComment(@RequestParam("id") UUID commentId) {
        commentService.deleteComment(commentId);
    }

}
