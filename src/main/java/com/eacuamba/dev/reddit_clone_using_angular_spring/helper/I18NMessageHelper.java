package com.eacuamba.dev.reddit_clone_using_angular_spring.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class I18NMessageHelper {
    private final MessageSource messageSource;

    public String getMessage(String key, String... args){
        return this.messageSource.getMessage(key, args, "No default message defined!", Locale.getDefault());
    }

    public String getMessage(String key, String defaultMessage, String[] args){
        return this.messageSource.getMessage(key, args, defaultMessage, Locale.getDefault());
    }

    public String getMessage(String key, String[] args, String defaultMessage, Locale locale){
        return this.messageSource.getMessage(key, args, defaultMessage, locale);
    }
}
