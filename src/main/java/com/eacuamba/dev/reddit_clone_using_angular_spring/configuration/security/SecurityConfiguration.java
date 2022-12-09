package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter.JwtTokenValidationFilter;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
    private final JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter;
    private final JwtTokenValidationFilter jwtTokenValidationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(this.jwtUsernameAndPasswordAuthenticationFilter)
                .addFilterAfter(this.jwtTokenValidationFilter, JwtUsernameAndPasswordAuthenticationFilter.class)

                .authorizeHttpRequests()
                .requestMatchers("/api/**")
                .authenticated()

                .requestMatchers("/api/auth/**", "/**")
                .permitAll();


        return httpSecurity.build();
    }
}
