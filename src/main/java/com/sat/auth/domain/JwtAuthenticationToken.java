package com.sat.auth.domain;

import com.sat.auth.config.jwt.TokenPair;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.List;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;
    private final TokenPair tokenPair;
    private final String providerName;

    private JwtAuthenticationToken(String principal, TokenPair tokenPair, List<? extends GrantedAuthority> grantedAuthorities, String providerName, boolean authenticated) {
        super(grantedAuthorities);
        setAuthenticated(authenticated);
        this.principal = principal;
        this.tokenPair = tokenPair;
        this.providerName = providerName;
    }

    public static JwtAuthenticationToken authenticatedToken(String principal, List<? extends GrantedAuthority> grantedAuthorities) {
        return authenticatedToken(principal, null, grantedAuthorities);
    }

    public static JwtAuthenticationToken authenticatedToken(String principal, TokenPair tokenPair, List<? extends GrantedAuthority> grantedAuthorities) {
        return new JwtAuthenticationToken(principal, tokenPair, grantedAuthorities, "", true);
    }

    public static JwtAuthenticationToken InsecureToken(String accessToken, String providerName) {
        return new JwtAuthenticationToken("", new TokenPair(accessToken, ""), Collections.emptyList(), providerName, false);
    }

    public static JwtAuthenticationToken unauthenticatedToken() {
        return new JwtAuthenticationToken(null, null, null, "", false);
    }

    @Override
    public String getCredentials() {
        if (tokenPair == null) {
            return "";
        }
        return tokenPair.accessToken();
    }

    @Override
    public String getPrincipal() {
        return principal;
    }
}

