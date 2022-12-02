package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtConfiguration;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtSecretKey;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.exception.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper.SecurityHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Configuration
@RequiredArgsConstructor
@Order(2)
public class JwtTokenValidationFilter extends OncePerRequestFilter {
    private final JwtSecretKey jwtSecretKey;
    private final JwtConfiguration jwtConfiguration;
    private final SecurityHelper securityHelper;
    private final UserDetailsService userDetailsService;

    @SuppressWarnings(value = {"unchecked"})
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(this.jwtConfiguration.getAuthorizationHeader());

        if (
                StringUtils.isNotBlank(authorizationHeader) ||
                        StringUtils.startsWith(authorizationHeader, this.jwtConfiguration.getTokenPrefix())
        ) {


            String token = authorizationHeader.replace(this.jwtConfiguration.getTokenPrefix(), "").trim();

            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(this.jwtSecretKey.secretKey())
                        .build()
                        .parseClaimsJws(token);

                Claims body = claimsJws.getBody();

                String username = body.getSubject();
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (
                        isNull(userDetails) ||
                                !userDetails.isEnabled() ||
                                !userDetails.isAccountNonExpired() ||
                                !userDetails.isAccountNonLocked() ||
                                !userDetails.isCredentialsNonExpired()
                ){
                    filterChain.doFilter(request, response);
                    return;
                }


                List<Map<String, String>> unparsedAuthorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> authorities = unparsedAuthorities.stream()
                        .map(v -> new SimpleGrantedAuthority(v.get("authority")))
                        .collect(Collectors.toSet());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        token,
                        userDetails.getAuthorities()
                );

                this.securityHelper.setAuthentication(usernamePasswordAuthenticationToken);
            } catch (JwtException jwtException) {
                throw new RedditCloneException("Exception occurred validating token: " + token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
