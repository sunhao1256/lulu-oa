package com.sh.lulu.auth.security.jwt;

import com.sh.lulu.auth.security.model.Token;
import com.sh.lulu.auth.security.repository.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";
    private static final String AUTHENTICATE_TYPE = "authenticate_type";
    private static final String AUTHENTICATE_ACCESS = "access";
    private static final String AUTHENTICATE_REFRESH = "refresh";

    private final String base64Secret;
    private final long tokenValidityInSeconds;
    private final long tokenValidityInSecondsForRefresh;
    private final TokenRepository tokenRepository;

    private Key key;


    public TokenProvider(
            @Value("${jwt.base64-secret}") String base64Secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.token-validity-in-seconds-for-refresh}") long tokenValidityInSecondsForRefresh,
            TokenRepository tokenRepository) {
        this.base64Secret = base64Secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.tokenValidityInSecondsForRefresh = tokenValidityInSecondsForRefresh;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Token createAccessToken(Authentication authentication) {
        return createToken(authentication, AUTHENTICATE_ACCESS, tokenValidityInSeconds);
    }

    public Token createRefresh(Authentication authentication) {
        return createToken(authentication, AUTHENTICATE_REFRESH, tokenValidityInSecondsForRefresh);
    }

    public Token createToken(Authentication authentication, String authenticateType, Long validTime) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + validTime * 1000);
        String value = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(AUTHENTICATE_TYPE, authenticateType)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
        return new Token(value, validTime, validity, authentication.getName(), authenticateType);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateAccessToken(String authToken) {
        return validateToken(authToken, s -> {
            if (StringUtils.hasText(s)) {
                return s.equals(AUTHENTICATE_ACCESS);
            }
            return false;
        });
    }

    public boolean validateRefreshToken(String authToken) {
        return validateToken(authToken, s -> {
            if (StringUtils.hasText(s)) {
                return s.equals(AUTHENTICATE_REFRESH);
            }
            return false;
        });
    }

    public boolean validateToken(String authToken, Predicate<String> predicateType) {
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken).getBody();
            String s = body.get(AUTHENTICATE_TYPE, String.class);
            return predicateType.test(s) && tokenRepository.exist(authToken);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public String getSubject(String authToken) {
        String subject = null;
        try {
            subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken).getBody().getSubject();
        } catch (Exception e) {
            log.info("JWT token are invalid.");
            log.trace("JWT token are invalid trace: {}", e);
        }
        return subject;
    }
}
