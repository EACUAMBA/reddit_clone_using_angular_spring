package com.eacuamba.dev.reddit_clone_using_angular_spring.api.resource;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.post.PostRequestMapper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.post.PostResponseMapper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.post.PostRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.response.post.PostResponse;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.PostRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/posts")
public class PostResource {
    private final PostRepository postRepository;
    private final PostService postService;
    private final PostResponseMapper postResponseMapper;
    private final PostRequestMapper postRequestMapper;

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll(){
        List<Post> posts = this.postRepository.findAll();
        List<PostResponse> postResponses = posts.stream()
                .map(this.postResponseMapper::mapToPostResponse)
                .toList();

        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        Post post = this.postService.findById(id);
        PostResponse postResponse = this.postResponseMapper.mapToPostResponse(post);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("find-by-user-username/{userUsername}")
    public ResponseEntity<List<PostResponse>> findByUserUsername(@PathVariable String userUsername) {
        List<Post> posts = this.postService.findByUserUsername(userUsername);
        List<PostResponse> postResponses = posts.stream()
                .map(this.postResponseMapper::mapToPostResponse)
                .toList();

        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponse> save(@Valid @RequestBody PostRequest postRequest) {
        Post post = this.postRequestMapper.mapToPost(postRequest);
        post = this.postService.save(post);
        PostResponse postResponse = this.postResponseMapper.mapToPostResponse(post);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<PostResponse> update(@PathVariable(value = "id") Long id, @Valid @RequestBody PostRequest postRequest){
        Post post = this.postRequestMapper.mapToPost(postRequest);
        post = post.toBuilder().id(id).build();
        post = this.postService.update(post);
        PostResponse postResponse = this.postResponseMapper.mapToPostResponse(post);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable Long id){
        this.postService.delete(id);
    }
}
