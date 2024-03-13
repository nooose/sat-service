package com.sat.user.infrastructure

import com.sat.user.domain.port.OAuthClient
import com.sat.user.domain.port.UserInfo
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class KakaoOAuthClient: OAuthClient {
    val restClient = RestClient.builder().build()

    override fun userInfo(token: String): UserInfo {
        return restClient.get()
            .uri("https://kapi.kakao.com/v1/oidc/userinfo")
            .headers {
                HttpHeaders.AUTHORIZATION to "Bearer $token"
            }
            .accept(APPLICATION_JSON)
            .retrieve()
            .body(KakaoUserInfo::class.java) ?: throw IllegalStateException("OAuth 사용자 정보를 받아올 수 없습니다.")
    }

    override fun supports(provider: String): Boolean {
        return "kakao" == provider
    }
}
