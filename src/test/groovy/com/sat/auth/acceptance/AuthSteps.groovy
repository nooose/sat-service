package com.sat.auth.acceptance

import io.restassured.RestAssured;

class AuthSteps {

    static def "토큰_발급_요청"(String id) {
        return RestAssured.given().param("id", id)
        .when().get("/fake/token")
        .then().log().all().extract().header("X-Access-Token")
    }
}
