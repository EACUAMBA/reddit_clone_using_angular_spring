package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.environment_helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class EnvironmentHelper {
    public Optional<String> getVariable(String name) {
        return Optional.ofNullable(System.getenv(name));
    }


}
