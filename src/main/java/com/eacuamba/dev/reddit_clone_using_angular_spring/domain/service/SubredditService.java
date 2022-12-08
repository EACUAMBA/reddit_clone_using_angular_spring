package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.RollbackTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.EntityNotFoundException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.ExceptionBuilder;
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
    private final ExceptionBuilder exceptionBuilder;
    private static final String SUBREDDIT_NAME = "Subreddit";

    @Transactional(readOnly = true)
    public Subreddit findById(Long id) {
        Optional<Subreddit> optionalSubreddit;
        if (isNull(id))
            optionalSubreddit = Optional.empty();
        else
            optionalSubreddit = this.subredditRepository.findById(id);

        EntityNotFoundException entityNotFoundException = this.exceptionBuilder.buildEntityNotFoundById(SUBREDDIT_NAME, id);
        return optionalSubreddit.orElseThrow(() -> entityNotFoundException);
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
        EntityNotFoundException entityNotFoundException = this.exceptionBuilder.buildEntityNotFoundById(SUBREDDIT_NAME, id);
        Subreddit oldSubreddit = optionalSubreddit.orElseThrow(() -> entityNotFoundException);
        this.beanHelper.copyNonNullProperties(subreddit, oldSubreddit);
        return this.subredditRepository.save(oldSubreddit);
    }

    @RollbackTransactional
    public void delete(Long id) {
        Optional<Subreddit> optionalSubreddit = this.subredditRepository.findById(id);
        EntityNotFoundException entityNotFoundException = this.exceptionBuilder.buildEntityNotFoundById(SUBREDDIT_NAME, id);
        Subreddit subreddit = optionalSubreddit.orElseThrow(() -> entityNotFoundException);
        this.subredditRepository.delete(subreddit);
    }
}
