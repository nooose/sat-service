package com.sat.auth;

import io.restassured.RestAssured;

public class AuthSteps {

    public static String 토큰_발급_요청(String id) {
        return RestAssured.given().param("id", id)
                .when().get("/fake/token")
                .then().log().all().extract().header("X-Access-Token");
    }
}
