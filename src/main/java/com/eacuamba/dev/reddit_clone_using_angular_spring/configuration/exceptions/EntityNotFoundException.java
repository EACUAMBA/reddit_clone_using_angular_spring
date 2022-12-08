package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions;

import lombok.*;

@Getter
@Setter
public class EntityNotFoundException extends RedditCloneException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException builder(String message){
        return new EntityNotFoundException(message);
    }
}
