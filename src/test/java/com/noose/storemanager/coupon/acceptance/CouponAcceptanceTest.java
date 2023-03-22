package com.noose.storemanager.coupon.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
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
    private int createCouponNumber = 3;

    @BeforeEach
    public void setUp() {
        super.setUp();
        createCoupons(createCouponNumber);
    }

    /**
     * When 쿠폰 생성을 요청하면
     * Then 생성된 쿠폰을 확인할 수 있다.
     */
    @Test
    void createCoupons() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "쿠폰");
        params.put("description", "테스트");
        ExtractableResponse<Response> response = given().log().all()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
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
        ExtractableResponse<Response> response = given().log().all()
                .when().get("/coupons")
                .then().log().all()
                .extract();

        assertAll(() -> {
            assertThat(response.jsonPath().getList("name").size()).isEqualTo(createCouponNumber);
            assertThat(response.jsonPath().getList("description").size()).isEqualTo(createCouponNumber);
        });
    }


    /*
     * When 쿠폰번호를 조회하면
     * Then 쿠폰번호에 해당하는 쿠폰을 확인할 수 있다.
     * */
    @Test
    void showCouponOfOne() {
        ExtractableResponse<Response> response = given().log().all()
                .when().get("/coupons/2")
                .then().log().all()
                .extract();

        assertAll(() -> {
            assertThat(response.jsonPath().getString("name")).isEqualTo("쿠폰");
            assertThat(response.jsonPath().getString("description")).isEqualTo("테스트");
        });
    }

    /*
     * When 쿠폰번호 삭제요청을 하면
     * Then 쿠폰이 삭제된다.
     * */
    @Test
    void deleteCoupon() {
        given().log().all()
            .when().delete("/coupons/1")
            .then().log().all()
            .extract();
        createCouponNumber -= 1;
        showCoupon();
    }

    private void createCoupons(int CREATE_COUPON_NUMBER) {
        for (int i = 0; i < CREATE_COUPON_NUMBER; i++) {
            createCoupons();
        }
    }
}
