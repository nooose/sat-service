package com.sat.auth.application;

import com.sat.auth.config.dto.KakaoOAuth2Response;
import com.sat.auth.config.dto.TokenRequest;
import com.sat.auth.config.jwt.JwtProcessor;
import com.sat.auth.config.jwt.TokenPair;
import com.sat.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final KakaoClient kakaoClient;
    private final MemberService memberService;
    private final JwtProcessor jwtProcessor;

    @Transactional
    public TokenPair login(TokenRequest tokenRequest) {
        KakaoOAuth2Response response = kakaoClient.response(tokenRequest.accessToken());
        memberService.joinIfNotExists(response.id(), response.kakaoAccount().profile().nickname());
        return jwtProcessor.createToken(response.id());
    }
}
