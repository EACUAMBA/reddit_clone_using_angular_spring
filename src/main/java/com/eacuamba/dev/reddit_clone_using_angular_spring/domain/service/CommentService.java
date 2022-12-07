package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.RollbackTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.builder.MailContentBuilder;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.exception.RedditCloneException;
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

    @RollbackTransactional
    public Comment save(Comment comment) {
        Optional<User> optionalUser = this.securityHelper.getAutheticatedUser();
        User user = optionalUser.orElseThrow(() -> new RedditCloneException("User not found!"));
        comment.setUser(user);

        comment = this.commentRepository.save(comment);
        String mailBody = this.mailContentBuilder
                .build(
                        comment.getPost().getUser().getUsername() + " posted a comment on your post. " + comment.getPost().getUrl(),
                        "New Comment! Go chek it out!"
                );
        this.mailService.sendCommentNotificationEmail(mailBody, user);
        return comment;
    }

    public List<Comment> findAllByPostId(Long postId) {
        Post post = this.postService.findById(postId);
        return this.commentRepository.findAllByPost(post);
    }

    public List<Comment> findAllByUserId(Long userId) {
        User user = this.userService.findById(userId);
        return this.commentRepository.findAllByUser(user);
    }
}
