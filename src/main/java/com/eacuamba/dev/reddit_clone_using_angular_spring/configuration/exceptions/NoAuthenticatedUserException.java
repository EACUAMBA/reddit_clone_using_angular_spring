package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions;


public class NoAuthenticatedUserException extends RedditCloneException{
    public NoAuthenticatedUserException(String message) {
        super(message);
    }
}
