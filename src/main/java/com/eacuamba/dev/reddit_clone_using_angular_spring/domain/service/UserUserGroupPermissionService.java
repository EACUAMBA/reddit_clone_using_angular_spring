package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.UserUserGroupPermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUserGroupPermissionService {
    private UserUserGroupPermissionRepository userUserGroupPermissionRepository;
}
