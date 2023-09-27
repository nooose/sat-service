package com.sat.auth.config.login;

import com.sat.auth.config.jwt.TokenPair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 토큰 쌍 저장 인메모리 임시 저장소<br>
 * 토큰 구조가 정해지면 테이블에 맞춰 구현 필요
 */
@Component
public class TokenRepository {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public void save(TokenPair tokenPair) {
        store.put(tokenPair.refreshToken(), tokenPair.accessToken());
    }

    public Optional<String> findAccessTokenByRefreshToken(String refreshToken) {
        return Optional.ofNullable(store.get(refreshToken));
    }
}
