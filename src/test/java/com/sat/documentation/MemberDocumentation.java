package com.sat.documentation;

import com.sat.member.application.MemberService;
import com.sat.member.application.dto.MemberResponse;
import io.restassured.RestAssured;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;


@DisplayName("사용자 API 문서화")
@ExtendWith(MockitoExtension.class)
class MemberDocumentation extends Documentation {

    @MockBean
    private MemberService memberService;

    @Test
    void me() {
        MemberResponse response = new MemberResponse("1000", "noose");
        BDDMockito.given(memberService.findBy(any())).willReturn(response);

        RestDocumentationFilter filter = document("me",
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 토큰")
            ),
            responseFields(
                fieldWithPath("id").description("사용자 ID"),
                fieldWithPath("name").description("사용자 이름")
            )
        );

        RestAssured
            .given(spec).log().all().filter(filter)
            .header("Authorization", "Bearer " + "{JWT}")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/members/me")
            .then().log().all().extract();
    }
}
