package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.RollbackTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.EntityNotFoundException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.ExceptionBuilder;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.PostRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.bean_helper.BeanHelper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final SecurityHelper securityHelper;
    private final BeanHelper beanHelper;
    private final ExceptionBuilder exceptionBuilder;
    private final UserService userService;

    public Post findById(Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        return optionalPost.orElseThrow(() -> this.exceptionBuilder.buildEntityNotFoundById("Post", id));
    }

    public Post save(Post post) {
        Optional<User> optionalUser = this.securityHelper.getAutheticatedUser();
        User user = optionalUser.orElseThrow(this.exceptionBuilder::buildNoAuthenticatedUser);
        post.setUser(user);
        return this.postRepository.save(post);
    }

    @RollbackTransactional
    public Post update(Post post) {
        Long id = post.getId();
        Optional<Post> optionalPost = this.postRepository.findById(id);
        EntityNotFoundException entityNotFoundException = this.exceptionBuilder.buildEntityNotFoundById("Post", id);
        Post oldPost = optionalPost.orElseThrow(() -> entityNotFoundException);
        this.beanHelper.copyNonNullProperties(post, oldPost);
        return this.postRepository.save(oldPost);
    }

    public void delete(Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        EntityNotFoundException entityNotFoundException = this.exceptionBuilder.buildEntityNotFoundById("Post", id);
        Post post = optionalPost.orElseThrow(() -> entityNotFoundException);
        this.postRepository.delete(post);
    }

    public List<Post> findByUserUsername(String userUsername) {
        User user = userService.loadUserByUsername(userUsername);
        return this.postRepository.findAllByUser(user);
    }
}
