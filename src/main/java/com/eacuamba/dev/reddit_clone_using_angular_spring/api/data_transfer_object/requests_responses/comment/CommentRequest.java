package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.requests_responses.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentRequest {
    @NotNull
    private String text;
    @NotNull
    private Long postId;
}
