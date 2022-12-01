package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.environment_helper;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentHelper {

    public String getVariable(String name) {
        return System.getenv(name);
    }

    @Bean
    public String mailUsername() {
        return System.getenv(EnvironmentNames.MAILTRAP_SMTP_USERNAME.getName());
    }

    @Bean
    public String mailPassword() {
        return System.getenv(EnvironmentNames.MAILTRAP_SMTP_PASSWORD.getName());
    }

}
