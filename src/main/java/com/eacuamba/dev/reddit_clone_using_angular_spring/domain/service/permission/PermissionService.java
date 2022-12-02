package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.permission;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
}
