package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = {Throwable.class})
public @interface CustomTransactional {
}
