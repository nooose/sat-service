package com.sat.documentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;


@WebMvcTest(LoginController.class)
class LoginControllerTest extends Documentation {

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("토큰 발급")
    @Test
    void token() throws Exception {
        LoginController.TokenRequest request = new LoginController.TokenRequest("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzd...");

        mockMvc.perform(post("/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcRestDocumentation.document("token",
                                requestFields(
                                        fieldWithPath("accessToken").description("소셜 액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("새로운 액세스 토큰"),
                                        fieldWithPath("refreshToken").description("새로운 리프레시 토큰")
                                )
                        )
                );
    }

    @DisplayName("토큰 갱신")
    @Test
    void refreshToken() throws Exception {
        LoginController.RefreshTokenRequest request = new LoginController.RefreshTokenRequest("refresh", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzd...");

        mockMvc.perform(post("/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcRestDocumentation.document("refresh-token",
                                requestFields(
                                        fieldWithPath("type").description("토큰 요청 타입"),
                                        fieldWithPath("refreshToken").description("발급받은 리프레시 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("새로운 액세스 토큰"),
                                        fieldWithPath("refreshToken").description("새로운 리프레시 토큰")
                                )
                        )
                );
    }
}
