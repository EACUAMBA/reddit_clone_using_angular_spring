package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderConfiguration {
    private final String mailHost;
    private final Integer mailPort;
    private final String protocol;
    private final String mailUsername;
    private final String mailPassword;

    public JavaMailSenderConfiguration(
            @Value("${mail.host}")     String mailHost,
            @Value("${mail.port}")     Integer mailPort,
            @Value("${mail.protocol}") String protocol,
            @Value("${mail.username}") String mailUsername,
            @Value("${mail.password}") String mailPassword
    ) {
        this.mailHost = mailHost;
        this.mailPort = mailPort;
        this.protocol = protocol;
        this.mailUsername = mailUsername;
        this.mailPassword = mailPassword;
    }


    @Bean
    public JavaMailSender javaMailSender(){
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(this.mailHost);
        javaMailSender.setPort(this.mailPort);
        javaMailSender.setProtocol(this.protocol);
        javaMailSender.setUsername(this.mailUsername);
        javaMailSender.setPassword(this.mailPassword);
        return javaMailSender;
    }
}
