package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.environment_helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class EnvironmentHelper {

    public String getVariable(String name) {
        return System.getenv(name);
    }

    @Bean
    public String mailUsername() {
        final String mailUsername = System.getenv(EnvironmentNames.MAILTRAP_SMTP_USERNAME.getName());
        if (Objects.isNull(mailUsername))
            log.error("The environment variable {} was not found in your system.", EnvironmentNames.MAILTRAP_SMTP_USERNAME);
        else
            log.error("For environment variable {} was found {} in your system.", EnvironmentNames.MAILTRAP_SMTP_USERNAME, mailUsername);
        return mailUsername;
    }

    @Bean
    public String mailPassword() {
        final String mailPassword = System.getenv(EnvironmentNames.MAILTRAP_SMTP_PASSWORD.getName());
        if (Objects.isNull(mailPassword))
            log.error("The environment variable {} was not found in your system.", EnvironmentNames.MAILTRAP_SMTP_PASSWORD);
        else
            log.error("For environment variable {} was found {} in your system.", EnvironmentNames.MAILTRAP_SMTP_USERNAME, mailPassword);
        return mailPassword;
    }

}
