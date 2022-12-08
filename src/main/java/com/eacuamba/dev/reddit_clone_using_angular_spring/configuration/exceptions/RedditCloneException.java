package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedditCloneException extends RuntimeException {
    public RedditCloneException(String message) {
        super(message);
    }
}
