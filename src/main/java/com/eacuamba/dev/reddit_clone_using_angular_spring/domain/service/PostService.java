package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.exception.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.PostRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.bean_helper.BeanHelper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final SecurityHelper securityHelper;
    private final BeanHelper beanHelper;

    public Post findById(Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        return optionalPost.orElseThrow(() -> new RedditCloneException("Post with id = %s not found."));
    }

    public Post save(Post post) {
        Optional<User> optionalUser = this.securityHelper.getAutheticatedUser();
        optionalUser.ifPresent(post::setUser);
        return this.postRepository.save(post);
    }

    public Post update(Post post) {
        Long id = post.getId();
        Optional<Post> optionalPost = this.postRepository.findById(id);
        RedditCloneException subredditNotFoundException = new RedditCloneException(String.format("Post with id equal to %s was not found.", id));
        Post oldPost = optionalPost.orElseThrow(() -> subredditNotFoundException);
        this.beanHelper.copyNonNullProperties(post, oldPost);
        return this.postRepository.save(oldPost);
    }

    public void delete(Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        RedditCloneException subredditNotFountERxception = new RedditCloneException(String.format("Post with id equal to %s was not found.", id));
        Post post = optionalPost.orElseThrow(() -> subredditNotFountERxception);
        this.postRepository.delete(post);
    }
}
