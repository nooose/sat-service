package com.noose.storemanager.coupon.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[E2E] 쿠폰 관련 기능")
public class CouponAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    /**
     * When 쿠폰 생성을 요청하면
     * Then 생성된 쿠폰을 확인할 수 있다.
     */
    @Test
    void createCoupon() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "쿠폰");
        params.put("description", "테스트");

        ExtractableResponse<Response> response = given().log().all()
                .body(params)
                .when().post("/coupons")
                .then().log().all()
                .extract();

        assertAll(() -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.jsonPath().getString("name")).isEqualTo("쿠폰");
            assertThat(response.jsonPath().getString("description")).isEqualTo("테스트");
        });
    }


    /**
     * When 쿠폰을 조회하면
     * Then 모든 쿠폰을 확인할 수 있다.
     */
    @Test
    void showCoupon() {

    }

}
