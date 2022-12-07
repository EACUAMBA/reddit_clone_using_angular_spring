package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.RollbackTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.exception.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.SubredditRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.bean_helper.BeanHelper;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper.SecurityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final BeanHelper beanHelper;
    private final SecurityHelper securityHelper;

    @Transactional(readOnly = true)
    public Subreddit findById(Long id) {
        Optional<Subreddit> optionalSubreddit;
        if (isNull(id))
            optionalSubreddit = Optional.empty();
        else
            optionalSubreddit = this.subredditRepository.findById(id);

        RedditCloneException subredditNotFountERxception = new RedditCloneException(String.format("Subreddit with id equal to %s was not found.", id));
        return optionalSubreddit.orElseThrow(() -> subredditNotFountERxception);
    }

    @RollbackTransactional
    public Subreddit save(Subreddit subreddit) {
        Optional<User> optionalUser = this.securityHelper.getAutheticatedUser();
        optionalUser.ifPresent(subreddit::setUser);
        return this.subredditRepository.save(subreddit);
    }

    @RollbackTransactional
    public Subreddit update(Subreddit subreddit) {
        Long id = subreddit.getId();
        Optional<Subreddit> optionalSubreddit = this.subredditRepository.findById(id);
        RedditCloneException subredditNotFoundException = new RedditCloneException(String.format("Subreddit with id equal to %s was not found.", id));
        Subreddit oldSubreddit = optionalSubreddit.orElseThrow(() -> subredditNotFoundException);
        this.beanHelper.copyNonNullProperties(subreddit, oldSubreddit);
        return this.subredditRepository.save(oldSubreddit);
    }

    @RollbackTransactional
    public void delete(Long id) {
        Optional<Subreddit> optionalSubreddit = this.subredditRepository.findById(id);
        RedditCloneException subredditNotFountERxception = new RedditCloneException(String.format("Subreddit with id equal to %s was not found.", id));
        Subreddit subreddit = optionalSubreddit.orElseThrow(() -> subredditNotFountERxception);
        this.subredditRepository.delete(subreddit);
    }
}
