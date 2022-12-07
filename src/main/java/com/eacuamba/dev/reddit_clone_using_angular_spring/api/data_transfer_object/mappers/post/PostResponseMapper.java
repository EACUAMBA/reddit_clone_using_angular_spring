package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.post;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.response.post.PostResponse;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public interface PostResponseMapper {

    @Mapping(target = "subredditName", expression = "java(getSubredditName(post.getSubreddit()))")
    @Mapping(target = "subredditId", expression = "java(getSubredditId(post.getSubreddit()))")
    @Mapping(target = "userUsername", expression = "java(getUserUsername(post.getUser()))")
    @Mapping(target = "userId", expression = "java(getUserId(post.getUser()))")
    PostResponse mapToPostResponse(Post post);

    default String getUserUsername(User user){
        if(isNull(user))
            return null;
        return user.getUsername();
    }
    default Long getUserId(User user){
        if(isNull(user))
            return null;
        return user.getId();
    }

    default String getSubredditName(Subreddit subreddit){
        if(isNull(subreddit))
            return null;
        return subreddit.getName();
    }
    default Long getSubredditId(Subreddit subreddit){
        if(isNull(subreddit))
            return null;
        return subreddit.getId();
    }

    @InheritInverseConfiguration
    @Mapping(target = "subreddit", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Post mapToPost(PostResponse postResponse);
}
