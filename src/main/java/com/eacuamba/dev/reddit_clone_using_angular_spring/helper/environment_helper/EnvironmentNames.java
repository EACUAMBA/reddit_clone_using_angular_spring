package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.environment_helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnvironmentNames {
    MAILTRAP_SMTP_USERNAME("MAILTRAP_SMTP_USERNAME"),
    MAILTRAP_SMTP_PASSWORD("MAILTRAP_SMTP_PASSWORD");

    private final String name;

}
