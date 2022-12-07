package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.subreddit;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.response.SubredditResponse;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public interface SubredditResponseMapper {

    @Mapping(target = "numberOfPosts", expression = "java(getPostListSize(subreddit.getPostList()))")
    @Mapping(target = "userUsername", expression = "java(getUserUsername(subreddit.getUser()))")
    @Mapping(target = "userId", expression = "java(getUserId(subreddit.getUser()))")
    SubredditResponse mapToSubredditResponse(Subreddit subreddit);

    default Integer getPostListSize(List<Post> postList){
        return postList.size();
    }
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

    //Inversion or mapping
    @InheritInverseConfiguration
    @Mapping(target = "postList", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subreddit mapToSubreddit(SubredditResponse subredditResponse);

}
