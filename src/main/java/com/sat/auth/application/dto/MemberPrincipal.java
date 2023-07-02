package com.sat.auth.application.dto;

import com.sat.auth.application.domain.RoleType;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record MemberPrincipal(
    String id,
    Map<String, Object> oAuth2Attributes,
    Collection<? extends GrantedAuthority> authorities
) implements OAuth2User {

    public static MemberPrincipal of(String id, OAuth2Response response) {
        var grantedAuthorities = Set.of(RoleType.USER).stream()
            .map(RoleType::getName)
            .map(SimpleGrantedAuthority::new)
            .toList();
        return new MemberPrincipal(id, response.getAttributes(), grantedAuthorities);
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
        return (String) oAuth2Attributes.get("id");
    }
}
