package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;
}
