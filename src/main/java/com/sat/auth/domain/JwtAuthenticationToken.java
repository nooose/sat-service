package com.sat.auth.domain;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    private JwtAuthenticationToken(String principal, Collection<? extends GrantedAuthority> grantedAuthorities, boolean authenticated) {
        super(grantedAuthorities);
        setAuthenticated(authenticated);
        this.principal = principal;
    }

    public static JwtAuthenticationToken authenticatedToken(String principal, Collection<? extends GrantedAuthority> grantedAuthorities) {
        return new JwtAuthenticationToken(principal, grantedAuthorities, true);
    }

    public static JwtAuthenticationToken unauthenticatedToken() {
        return new JwtAuthenticationToken(null, null, false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }
}
