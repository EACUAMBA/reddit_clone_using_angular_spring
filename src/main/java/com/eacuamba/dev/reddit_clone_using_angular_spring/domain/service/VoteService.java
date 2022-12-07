package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.exception.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.vote.Vote;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.vote.VoteType;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.VoteRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper.SecurityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostService postService;
    private final SecurityHelper securityHelper;

    public void vote(Vote vote) {
        Optional<User> optionalUser = this.securityHelper.getAutheticatedUser();
        User authenticatedUser = optionalUser.orElseThrow(() -> new RedditCloneException("User not fount"));
        vote.setUser(authenticatedUser);

        Post votePost = vote.getPost();
        Optional<Vote> optionalTopVote = this.voteRepository
                .findTopByPostAndUserOrderByIdDesc(
                        votePost,
                        authenticatedUser
                );

        if(optionalTopVote.isPresent()){
            Vote lastVote = optionalTopVote.get();
            if(lastVote.getVoteType().equals(vote.getVoteType())){
                throw new RedditCloneException("You already votted this post " + lastVote);
            }
        }

        if(VoteType.UP.equals(vote.getVoteType()))
            votePost.setVoteCount(votePost.getVoteCount() + 1);
        else
            votePost.setVoteCount(votePost.getVoteCount() - 1);

        this.postService.update(votePost);
        this.voteRepository.save(vote);
    }
}
