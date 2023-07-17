package com.sat.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public record OAuth2MemberPrincipal(
    String id,
    String nickname,
    Map<String, Object> oAuth2Attributes,
    Collection<? extends GrantedAuthority> authorities
) implements OAuth2User {

    public static OAuth2MemberPrincipal of(String id, String nickname, Map<String, Object> oAuth2Attributes) {
        var grantedAuthorities = Set.of(RoleType.MEMBER).stream()
            .map(RoleType::getName)
            .map(SimpleGrantedAuthority::new)
            .toList();
        return new OAuth2MemberPrincipal(id, nickname, oAuth2Attributes, grantedAuthorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return id;
    }
}
