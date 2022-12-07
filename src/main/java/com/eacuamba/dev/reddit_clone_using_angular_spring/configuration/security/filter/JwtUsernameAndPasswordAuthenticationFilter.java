package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter;


import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.UsernameAndPasswordAuthenticationRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtConfiguration;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.DateTimeHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Slf4j
@Order(1)
@RequiredArgsConstructor
@Configuration
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;
    private final DateTimeHelper dateTimeHelper;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
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
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = (String) authResult.getPrincipal();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(this.dateTimeHelper.getCurrenteDate())
                .setExpiration(this.dateTimeHelper.convertLocalDateToDate(
                                this.dateTimeHelper.getCurrentLocalDate().plusDays(
                                        this.jwtConfiguration.getExpirationAfterDays()
                                )
                        )
                )
                .signWith(this.secretKey)
                .compact();

        response.addHeader(
                this.jwtConfiguration.getAuthorizationHeader(),
                String.format(
                        "%s %s",
                        this.jwtConfiguration.getTokenPrefix(),
                        token
                )
        );
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
