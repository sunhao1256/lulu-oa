package com.sh.lulu.auth.security.repository;

import com.sh.lulu.auth.security.model.Token;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class TokenRepository {
    private final static String TOKEN_PREFIX = "token:";
    private final RedisTemplate<String, Token> redisTemplate;

    public Boolean exist(String token) {
        return redisTemplate.opsForValue().get(TOKEN_PREFIX + token) != null;
    }

    public Boolean delete(String token) {
        return redisTemplate.delete(TOKEN_PREFIX + token);
    }

    public List<Token> listToken() {
        Set<String> keys = redisTemplate.keys(TOKEN_PREFIX+"*");
        if (ObjectUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public void save(Token token) {
        redisTemplate.opsForValue().set(TOKEN_PREFIX + token.getValue(), token, token.getExpire(), TimeUnit.SECONDS);
    }

}
