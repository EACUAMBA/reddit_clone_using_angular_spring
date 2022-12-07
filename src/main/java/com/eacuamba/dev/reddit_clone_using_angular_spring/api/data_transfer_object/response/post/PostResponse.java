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
    private Integer voteCount;
    private String subredditName;
    private Long subredditId;
    private String userUsername;
    private Long userId;

}
