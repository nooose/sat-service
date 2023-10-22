package com.sat.auth.config.login;

import com.sat.auth.application.JwtProcessor;
import com.sat.auth.application.dto.TokenPair;
import com.sat.auth.domain.MemberRole;
import com.sat.auth.domain.MemberRoleRepository;
import com.sat.auth.domain.RefreshToken;
import com.sat.auth.domain.RefreshTokenRepository;
import com.sat.member.domain.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtRefreshProvider implements AuthenticationProvider {

    private final JwtProcessor jwtProcessor;
    private final RefreshTokenRepository tokenRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String refreshToken = (String) authentication.getCredentials();
        RefreshToken token = getRefreshTokenInfo(refreshToken);

        MemberId memberId = token.getMemberId();
        MemberRole memberRole = getMemberRole(memberId);

        List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(memberRole.getRole().getRoleName());
        TokenPair newTokenPair = jwtProcessor.createToken(memberId.getId(), roles);
        return JwtAuthenticationToken.authenticatedToken(memberId.getId(), newTokenPair, roles);
    }

    private RefreshToken getRefreshTokenInfo(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BadCredentialsException("갱신 정보가 올바르지 않습니다."));
    }

    private MemberRole getMemberRole(MemberId memberId) {
        return memberRoleRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BadCredentialsException("권한 정보를 찾을 수 없습니다. - " + memberId.getId()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
