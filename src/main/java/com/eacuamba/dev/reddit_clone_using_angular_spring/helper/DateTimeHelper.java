package com.eacuamba.dev.reddit_clone_using_angular_spring.helper;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DateTimeHelper {
    public Instant getCurrentInstant(){
        return Instant.now();
    }
}
