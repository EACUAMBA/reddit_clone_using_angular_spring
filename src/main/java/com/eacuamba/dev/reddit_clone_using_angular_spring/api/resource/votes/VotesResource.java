package com.eacuamba.dev.reddit_clone_using_angular_spring.api.resource.votes;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.vote.VoteMapper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.vote.VoteRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.vote.Vote;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/votes")
@RequiredArgsConstructor
public class VotesResource {
    private final VoteService voteService;
    private final VoteMapper voteMapper;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteRequest voteRequest ){
        Vote vote = this.voteMapper.mapToVote(voteRequest);
        this.voteService.vote(vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
