package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.filter;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtConfiguration;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtSecretKey;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.Token;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.TokenRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.token.TokenService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper.SecurityHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Order(2)
public class JwtTokenValidationFilter extends OncePerRequestFilter {
    private final JwtSecretKey jwtSecretKey;
    private final JwtConfiguration jwtConfiguration;
    private final SecurityHelper securityHelper;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    @SuppressWarnings(value = {"unchecked"})
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(this.jwtConfiguration.getAuthorizationHeader());
        String tokenValue = null;
        Token token = null;

        if (
                StringUtils.isNotBlank(authorizationHeader) || StringUtils.startsWith(authorizationHeader, this.jwtConfiguration.getTokenPrefix())
        ) {
            tokenValue = authorizationHeader.replace(this.jwtConfiguration.getTokenPrefix(), "").trim();
            Optional<Token> optionalToken = this.tokenRepository.findByValue(tokenValue);
            token = optionalToken.orElse(null);
        }

        if (
                nonNull(token) && Boolean.TRUE.equals(token.getValid())
        ) {
            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(this.jwtSecretKey.secretKey())
                        .build()
                        .parseClaimsJws(tokenValue);

                Claims body = claimsJws.getBody();

                String username = body.getSubject();
                UserDetails userDetails = this.userService.loadUserByUsername(username);
                if (
                        isNull(userDetails) ||
                                !userDetails.isEnabled() ||
                                !userDetails.isAccountNonExpired() ||
                                !userDetails.isAccountNonLocked() ||
                                !userDetails.isCredentialsNonExpired()
                ) {
                    filterChain.doFilter(request, response);
                    return;
                }


                List<Map<String, String>> unparsedAuthorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> authorities = unparsedAuthorities.stream()
                        .map(v -> new SimpleGrantedAuthority(v.get("authority")))
                        .collect(Collectors.toSet());

                Set<SimpleGrantedAuthority> serverAuthorities = userDetails.getAuthorities().stream().map((grantedAuthority -> new SimpleGrantedAuthority(grantedAuthority.getAuthority()))).collect(Collectors.toSet());
                boolean equalCollection = CollectionUtils.isEqualCollection(authorities, serverAuthorities);
                if (!equalCollection) {
                    token.setValid(Boolean.FALSE);
                    this.tokenService.save(token);
                    log.info("The JWT authorities are different from the authorities at the server.");
                }

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        tokenValue,
                        serverAuthorities
                );

                this.securityHelper.setAuthentication(usernamePasswordAuthenticationToken);
            } catch (JwtException jwtException) {
                throw new RedditCloneException("Exception occurred validating token: " + tokenValue);
            }
        }
        filterChain.doFilter(request, response);
    }
}
