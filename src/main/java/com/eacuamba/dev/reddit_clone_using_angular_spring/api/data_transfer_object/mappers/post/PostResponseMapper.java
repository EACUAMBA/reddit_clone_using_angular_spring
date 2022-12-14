package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.post;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.response.post.PostResponse;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.vote.Vote;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.vote.VoteType;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.CommentRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.VoteRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import com.github.kevinsawicki.timeago.TimeAgo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public abstract class PostResponseMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TimeAgo timeAgo;

    @Mapping(target = "subredditName", expression = "java(getSubredditName(post.getSubreddit()))")
    @Mapping(target = "subredditId", expression = "java(getSubredditId(post.getSubreddit()))")
    @Mapping(target = "userUsername", expression = "java(getUserUsername(post.getUser()))")
    @Mapping(target = "userId", expression = "java(getUserId(post.getUser()))")
    @Mapping(target = "commentsCount", expression = "java(countComments(post))")
    @Mapping(target = "duration", expression = "java(duration(post))")
    @Mapping(target = "upVote", expression = "java(setUpVote(post))")
    @Mapping(target = "downVote", expression = "java(setDownVote(post))")
    public abstract PostResponse mapToPostResponse(Post post);

    public Boolean setDownVote(Post post) {
        Optional<Vote> optionalVote = this.voteRepository.findTopByPostAndUserOrderByIdDesc(post, post.getUser());
        if (optionalVote.isPresent() && optionalVote.get().getVoteType().equals(VoteType.DOWN)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean setUpVote(Post post) {
        Optional<Vote> optionalVote = this.voteRepository.findTopByPostAndUserOrderByIdDesc(post, post.getUser());
        if (optionalVote.isPresent() && optionalVote.get().getVoteType().equals(VoteType.UP)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public String getUserUsername(User user) {
        if (isNull(user))
            return null;
        return user.getUsername();
    }

    public Long getUserId(User user) {
        if (isNull(user))
            return null;
        return user.getId();
    }

    public String getSubredditName(Subreddit subreddit) {
        if (isNull(subreddit))
            return null;
        return subreddit.getName();
    }

    public Long getSubredditId(Subreddit subreddit) {
        if(isNull(subreddit))
            return null;
        return subreddit.getId();
    }

    public Integer countComments(Post post){
        return this.commentRepository.countByPost(post);
    }

    public String duration(Post post){
        return this.timeAgo.timeAgo(Date.from(post.getCreatedAt()));
    }

    @InheritInverseConfiguration
    @Mapping(target = "subreddit", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Post mapToPost(PostResponse postResponse);
}
