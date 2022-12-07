package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.vote;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.vote.Vote;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteMapper {
    private final ModelMapper modelMapper;
    private final PostService postService;

    public Vote mapToVote(VoteRequest voteRequest){
        Vote vote = modelMapper.map(voteRequest, Vote.class);

        Post post = this.postService.findById(voteRequest.getPostId());
        vote.setPost(post);

        return vote;
    }
}
