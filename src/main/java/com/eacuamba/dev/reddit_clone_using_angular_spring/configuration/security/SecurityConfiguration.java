package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter.JwtTokenValidationFilter;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter;
    private final JwtTokenValidationFilter jwtTokenValidationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(this.jwtUsernameAndPasswordAuthenticationFilter)
                .addFilterAfter(this.jwtTokenValidationFilter, JwtUsernameAndPasswordAuthenticationFilter.class)
                //Here I authorize all requests to api/auth/**;
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**")
                .permitAll()
                //Enabling access to static files
                .requestMatchers("/", "index", "css/*", "js/*")
                .permitAll()
                //Here I'm blocking all rest of request to only authenticated people.
                .anyRequest()
                .authenticated();

        return httpSecurity.build();
    }
}
