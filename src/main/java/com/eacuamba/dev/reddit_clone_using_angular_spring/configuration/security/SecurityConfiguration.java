package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                //Here I authorize all requests to api/auth/**;
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**")
                .permitAll()
                //Here I'm blocking all rest of request to only authenticated people.
                .anyRequest()
                .authenticated();

        return httpSecurity.build();
    }
}
