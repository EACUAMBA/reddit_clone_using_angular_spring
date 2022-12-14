package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.requests_responses.comment;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Comment;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.PostService;
import com.github.kevinsawicki.timeago.TimeAgo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final TimeAgo timeAgo;

    public Comment mapTo(CommentRequest commentRequest){
        Comment comment = this.modelMapper.map(commentRequest, Comment.class);
        comment.setPost(this.postService.findById(commentRequest.getPostId()));
        return comment;
    }

    public CommentResponse mapToCommentResponse(Comment comment){
        CommentResponse commentResponse = this.modelMapper.map(comment, CommentResponse.class);
        commentResponse.setPostId(comment.getPost().getId());
        commentResponse.setUserId(comment.getUser().getId());
        commentResponse.setUserUsername(comment.getUser().getUsername());
        commentResponse.setTimeAgo(this.timeAgo.timeAgo(comment.getCreatedAt().toEpochMilli()));
        return commentResponse;
    }
}
