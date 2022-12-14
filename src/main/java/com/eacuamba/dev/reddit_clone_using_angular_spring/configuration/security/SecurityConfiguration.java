package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter.JwtTokenValidationFilter;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfiguration {
    private final JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter;
    private final JwtTokenValidationFilter jwtTokenValidationFilter;
    private final CorsConfigurationSource configurationSource;

    public SecurityConfiguration(
            JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter,
            JwtTokenValidationFilter jwtTokenValidationFilter,
            @Qualifier(value = "corsConfigurationSource")
            CorsConfigurationSource configurationSource) {
        this.jwtUsernameAndPasswordAuthenticationFilter = jwtUsernameAndPasswordAuthenticationFilter;
        this.jwtTokenValidationFilter = jwtTokenValidationFilter;
        this.configurationSource = configurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .cors()
                .configurationSource(this.configurationSource)
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilter(this.jwtUsernameAndPasswordAuthenticationFilter)
                .addFilterAfter(this.jwtTokenValidationFilter, JwtUsernameAndPasswordAuthenticationFilter.class)

                .authorizeHttpRequests()

                .requestMatchers("/api/auth/**")
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/api/subreddits", "/api/posts", "/api/posts/**")
                .permitAll()

                .requestMatchers("/api/**")
                .authenticated()

                .requestMatchers("/**")
                .permitAll();


        return httpSecurity.build();
    }
}
