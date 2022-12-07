package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.subreddit;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.subreddit.SubredditRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditResquestMapper {
    SubredditRequest mapToSubredditResquest(Subreddit subreddit);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postList", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subreddit mapToSubreddit(SubredditRequest subredditRequest);
}
