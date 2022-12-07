package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class SubredditResponse {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private String userUsername;
    private Long userId;
}
