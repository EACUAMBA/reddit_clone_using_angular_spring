package com.eacuamba.dev.reddit_clone_using_angular_spring.api.resource.comments;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.requests_responses.comment.CommentMapper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.requests_responses.comment.CommentRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.requests_responses.comment.CommentResponse;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Comment;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/comments")
public class CommentResource {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CommentRequest commentRequest) {
        Comment comment = commentMapper.mapTo(commentRequest);
        comment = this.commentService.save(comment);
        this.commentMapper.mapToCommentResponse(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "by-post-id/{postId}")
    public ResponseEntity<List<CommentResponse>> findAllByPostId(@PathVariable Long postId) {
        List<Comment> commentList = this.commentService.findAllByPostId(postId);
        List<CommentResponse> commentResponseList = commentList.stream()
                .map(this.commentMapper::mapToCommentResponse)
                .toList();

        return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
    }

    @GetMapping(path = "by-user-id/{userId}")
    public ResponseEntity<List<CommentResponse>> findAllByUserId(@PathVariable Long userId) {
        List<Comment> commentList = this.commentService.findAllByUserId(userId);
        List<CommentResponse> commentResponseList = commentList.stream()
                .map(this.commentMapper::mapToCommentResponse)
                .toList();

        return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
    }
}
