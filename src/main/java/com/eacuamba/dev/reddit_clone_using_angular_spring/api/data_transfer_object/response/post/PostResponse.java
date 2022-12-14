package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PostResponse {
    private Long id;
    private String name;
    private String url;
    private String description;
    private String subredditName;
    private Long subredditId;
    private String userUsername;
    private Long userId;

    private Integer voteCount;
    private Integer commentsCount;
    private String duration;
    private Boolean upVote;
    private Boolean downVote;
}
