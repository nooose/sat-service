package com.sat.auth.config.login;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

@Getter
public class RefreshAuthenticationToken extends AbstractAuthenticationToken {

    private final String refreshToken;

    public static RefreshAuthenticationToken from(String refreshToken) {
        return new RefreshAuthenticationToken(refreshToken);
    }

    private RefreshAuthenticationToken(String refreshToken) {
        super(Collections.emptyList());
        this.refreshToken = refreshToken;
    }

    @Override
    public String getCredentials() {
        return refreshToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
