package com.sat.auth.application.dto;

import com.sat.auth.application.domain.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public record MemberPrincipal(
    String id,
    Map<String, Object> oAuth2Attributes,
    Collection<? extends GrantedAuthority> authorities
) implements OAuth2User {

    public static MemberPrincipal of(String id, Map<String, Object> oAuth2Attributes) {
        var grantedAuthorities = Set.of(RoleType.USER).stream()
            .map(RoleType::getName)
            .map(SimpleGrantedAuthority::new)
            .toList();
        return new MemberPrincipal(id, oAuth2Attributes, grantedAuthorities);
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
        return String.valueOf(oAuth2Attributes.get("id"));
    }
}
