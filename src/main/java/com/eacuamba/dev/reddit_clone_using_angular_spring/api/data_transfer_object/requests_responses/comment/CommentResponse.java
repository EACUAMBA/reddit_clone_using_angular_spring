package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.requests_responses.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentResponse {
    private Long id;
    private String text;
    private Long postId;
    private Long userId;
    private String userUsername;
}
