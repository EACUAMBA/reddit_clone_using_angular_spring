package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration;

import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Transactional(rollbackFor = {Throwable.class})
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Reflective
public @interface CustomTransactional {
}
