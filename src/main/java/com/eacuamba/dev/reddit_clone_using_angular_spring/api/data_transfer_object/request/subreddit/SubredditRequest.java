package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.subreddit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditRequest {
    @NotBlank(message = "Name of a subreddit community cannot be empty!")
    private String name;
    @NotBlank(message = "Description is required!")
    private String description;
}
