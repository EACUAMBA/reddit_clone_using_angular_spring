package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.post;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.post.PostRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.SubredditService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRequestMapper {
    private final ModelMapper modelMapper;
    private final SubredditService subredditService;
    private final UserService userService;

    public Post mapToPost(PostRequest postRequest){
        Post post = this.modelMapper.map(postRequest, Post.class);

        Subreddit subreddit = subredditService.findById(postRequest.getSubredditId());
        post.setSubreddit(subreddit);

        return post;
    }
}
