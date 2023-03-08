package com.sh.lulu.auth.security.rest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.lulu.auth.security.UserModelDetailsService;
import com.sh.lulu.auth.security.jwt.JWTFilter;
import com.sh.lulu.auth.security.jwt.TokenProvider;
import com.sh.lulu.auth.security.model.QUser;
import com.sh.lulu.auth.security.model.Token;
import com.sh.lulu.auth.security.repository.TokenRepository;
import com.sh.lulu.auth.security.repository.UserRepository;
import com.sh.lulu.auth.security.rest.dto.LoginDto;
import com.sh.lulu.auth.security.service.UserService;
import com.sh.lulu.common.response.CommonResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationRestController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserModelDetailsService userModelDetailsService;

    private final TokenRepository tokenRepository;

    private final UserService userService;


    @PostMapping("/authenticate")
    public ResponseEntity<CommonResult<JWTToken>> authorize(@Validated @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        userService.updateLastSignIn(loginDto.getUsername());

        return create(authentication);
    }


    @PostMapping("/extend")
    public ResponseEntity<CommonResult<JWTToken>> extend(HttpServletRequest httpServletRequest) {
        String token = JWTFilter.resolveToken(httpServletRequest);

        if (StringUtils.hasText(token) && tokenProvider.validateRefreshToken(token)) {
            String userName = tokenProvider.getSubject(token);
            if (userName != null) {
                UserDetails userDetails = userModelDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userName, "", userDetails.getAuthorities());
                return create(authenticationToken);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Full authentication is required to access this resource");

    }

    private ResponseEntity<CommonResult<JWTToken>> create(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Token accessToken = tokenProvider.createAccessToken(authentication);
        Token refreshToken = tokenProvider.createRefresh(authentication);

        tokenRepository.save(accessToken);
        tokenRepository.save(refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        return new ResponseEntity<>(CommonResult.success(new JWTToken(accessToken.getValue(), refreshToken.getValue()))
                , httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    @AllArgsConstructor
    @Data
    static class JWTToken {

        private String accessToken;
        private String refreshToken;
    }


    @GetMapping("/listAllToken")
    public List<Token> listMyToken() {
        return tokenRepository.listToken();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class DeleteToken {
        @NotBlank
        private String token;
    }

    @DeleteMapping("/deleteToken")
    public void listMyToken(@RequestBody @Validated DeleteToken deleteToken) {
        tokenRepository.delete(deleteToken.getToken());
    }
}
