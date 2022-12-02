package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.environment_helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum EnvironmentNames {
    MAILTRAP_SMTP_USERNAME("MAILTRAP_SMTP_USERNAME"),
    MAILTRAP_SMTP_PASSWORD("MAILTRAP_SMTP_PASSWORD");

    private final String name;

    @Override
    public String toString() {
        return Objects.nonNull(this.name) ? this.getName() : this.name();
    }
}
