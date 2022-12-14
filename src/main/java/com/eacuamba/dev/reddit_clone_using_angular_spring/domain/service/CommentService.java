package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.RollbackTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.ExceptionBuilder;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.builder.MailContentBuilder;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Comment;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.CommentRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;
    private final SecurityHelper securityHelper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final ExceptionBuilder exceptionBuilder;

    @RollbackTransactional
    public Comment save(Comment comment) {
        User postUserOwner = comment.getPost().getUser();

        Optional<User> optionalUser = this.securityHelper.getAutheticatedUser();
        User user = optionalUser.orElseThrow(this.exceptionBuilder::buildNoAuthenticatedUser);
        comment.setUser(user);

        comment = this.commentRepository.save(comment);
        String mailBody = this.mailContentBuilder
                .build(
                        comment.getUser().getUsername() + " posted a comment on your post. " + comment.getPost().getName(),
                        "New Comment! Go chek it out!"
                );
        this.mailService.sendCommentNotificationEmail(mailBody, postUserOwner);
        return comment;
    }

    public List<Comment> findAllByPostId(Long postId) {
        Post post = this.postService.findById(postId);
        return this.commentRepository.findAllByPost(post);
    }

    public List<Comment> findAllByUserUsername(String userUsername) {
        User user = this.userService.findByUsername(userUsername);
        return this.commentRepository.findAllByUser(user);
    }
}
