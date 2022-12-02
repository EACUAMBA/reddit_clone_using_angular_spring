package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.module;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
}
