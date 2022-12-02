package com.eacuamba.dev.reddit_clone_using_angular_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class RedditCloneUsingAngularSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneUsingAngularSpringApplication.class, args);
	}

}
