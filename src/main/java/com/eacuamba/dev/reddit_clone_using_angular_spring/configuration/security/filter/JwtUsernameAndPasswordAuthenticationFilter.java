package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter;


import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.UsernameAndPasswordAuthenticationRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtConfiguration;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.Token;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.token.TokenService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(1)
@RequiredArgsConstructor
@Configuration
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        this.setFilterProcessesUrl("/api/auth/login");
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest usernameAndPasswordAuthenticationRequest = this.objectMapper.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usernameAndPasswordAuthenticationRequest.getUsername(),
                    usernameAndPasswordAuthenticationRequest.getPassword()
            );

            return this.authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            log.error("Error: ", e);
            throw new RedditCloneException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        String username = (String) authResult.getPrincipal();
        User user = this.userService.loadUserByUsername(username);
        String jwt = this.jwtService.generateJwt(user);
        Token token = this.tokenService.generateRefreshToken(user);

        response.addHeader(
                this.jwtConfiguration.getAuthorizationHeader(),
                String.format(
                        "%s %s",
                        this.jwtConfiguration.getTokenPrefix(),
                        jwt
                )
        );
        response.addHeader(
                "Refresh-token",
                token.getValue()
        );
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
