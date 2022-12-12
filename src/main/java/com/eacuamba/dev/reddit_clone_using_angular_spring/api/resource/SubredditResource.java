package com.eacuamba.dev.reddit_clone_using_angular_spring.api.resource;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.subreddit.SubredditResponseMapper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.subreddit.SubredditResquestMapper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.subreddit.SubredditRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.response.SubredditResponse;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.SubredditRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.SubredditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/subreddits")
public class SubredditResource {
    private final SubredditRepository subredditRepository;
    private final SubredditService subredditService;
    private final SubredditResponseMapper subredditResponseMapper;
    private final SubredditResquestMapper subredditResquestMapper;

    @GetMapping
    public ResponseEntity<List<SubredditResponse>> findAll(){
        List<Subreddit> subredditList = this.subredditRepository.findAll();

        List<SubredditResponse> subredditResponseList = subredditList.stream()
                .map(this.subredditResponseMapper::mapToSubredditResponse)
                .toList();

        return new ResponseEntity<>(subredditResponseList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SubredditResponse> findById(@PathVariable Long id){
        Subreddit subreddit =  this.subredditService.findById(id);

        SubredditResponse subredditResponse = this.subredditResponseMapper.mapToSubredditResponse(subreddit);

        return new ResponseEntity<>(subredditResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubredditResponse> save(@Valid @RequestBody SubredditRequest subredditRequest){
        Subreddit subreddit = this.subredditResquestMapper.mapToSubreddit(subredditRequest);
        subreddit = this.subredditService.save(subreddit);
        SubredditResponse subredditResponse = this.subredditResponseMapper.mapToSubredditResponse(subreddit);
        return new ResponseEntity<>(subredditResponse, HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<SubredditResponse> update(@PathVariable(value = "id") Long id, @Valid @RequestBody SubredditRequest subredditRequest){
        Subreddit subreddit = this.subredditResquestMapper.mapToSubreddit(subredditRequest);
        subreddit = subreddit.toBuilder().id(id).build();
        subreddit = this.subredditService.update(subreddit);
        SubredditResponse subredditResponse = this.subredditResponseMapper.mapToSubredditResponse(subreddit);
        return new ResponseEntity<>(subredditResponse, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable Long id){
        this.subredditService.delete(id);
    }

}
