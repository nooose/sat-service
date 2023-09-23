package com.sat.auth.application;

import com.sat.auth.config.dto.KakaoOAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class KakaoUserInfoClient implements UserInfoClient {

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;
    private final RestTemplate restTemplate;

    @Override
    public KakaoOAuth2Response response(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        var request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(userInfoUri));
        var response = restTemplate.exchange(request, KakaoOAuth2Response.class);
        return response.getBody();
    }

    @Override
    public boolean isSupported(String providerName) {
        return providerName.equals("kakao");
    }
}
