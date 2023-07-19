package com.sat.member.acceptance

import com.sat.AcceptanceTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

import static com.sat.auth.acceptance.AuthSteps.토큰_발급_요청
import static com.sat.member.MemberFixtures.일반_사용자
import static io.restassured.RestAssured.given

class MemberAcceptanceTest extends AcceptanceTest {

    def "발급받은 토큰으로 자신의 정보를 확인할 수 있다."() {
        given:
        def token = 토큰_발급_요청(일반_사용자.getId())

        when:
        def response = given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/members/me")
                .then().log().all()

        then:
        response.statusCode(HttpStatus.OK.value())
        response.extract().jsonPath().getString("id") == 일반_사용자.getId()
        response.extract().jsonPath().getString("name") == 일반_사용자.getName().getValue()
    }

    def "유효하지 않은 토큰으로 자신의 정보를 확인할 수 없다."() {
        when:
        def response = given().log().all()
                .header("Authorization", "Bearer " + "invalidToken")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/members/me")
                .then().log().all()

        then:
        response.statusCode(HttpStatus.UNAUTHORIZED.value())
    }
}
