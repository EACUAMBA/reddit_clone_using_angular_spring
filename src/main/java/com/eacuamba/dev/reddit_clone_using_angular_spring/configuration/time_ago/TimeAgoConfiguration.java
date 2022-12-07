package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.time_ago;

import com.github.kevinsawicki.timeago.TimeAgo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeAgoConfiguration {
    @Bean
    public TimeAgo timeAgo(){
        return new TimeAgo();
    }
}
