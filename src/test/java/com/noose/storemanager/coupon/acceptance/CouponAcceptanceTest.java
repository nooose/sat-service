package com.noose.storemanager.coupon.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/coupons")
                .then().log().all()
                .extract();

        String[] location = response.headers().get("Location").getValue().split("/");
        int createdCouponId = Integer.parseInt(location[location.length - 1]);
        ExtractableResponse<Response> couponResponse = findCoupon(createdCouponId);

        assertAll(() -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(couponResponse.jsonPath().getInt("id")).isEqualTo(createdCouponId);
        });
    }

    private void createCoupon(int createCouponNumber) {
        for (int i = 0; i < createCouponNumber; i++) {
            Map<String, String> params = new HashMap<>();
            params.put("name", "쿠폰");
            params.put("description", "테스트");
            given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(params)
                    .when().post("/coupons")
                    .then().log().all()
                    .extract();
        }
    }

    /**
     * When 쿠폰을 조회하면
     * Then 모든 쿠폰을 확인할 수 있다.
     */
    @Test
    void showCoupon() {
        int createCouponNumber = 4;
        createCoupon(createCouponNumber);

        ExtractableResponse<Response> response = given().log().all()
                .when().get("/coupons")
                .then().log().all()
                .extract();

        assertThat(response.jsonPath().getList("id").size()).isEqualTo(createCouponNumber);
    }

    /*
     * When 쿠폰번호를 조회하면
     * Then 쿠폰번호에 해당하는 쿠폰을 확인할 수 있다.
     * */
    @Test
    void showCouponOfOne() {
        int createCouponNumber = 6;
        createCoupon(createCouponNumber);

        int findCouponId = 2;
        ExtractableResponse<Response> response = findCoupon(findCouponId);

        assertThat(response.jsonPath().getInt("id")).isEqualTo(2);
    }

    private ExtractableResponse<Response> findCoupon(int id) {
        return given().log().all()
                .when().get("/coupons/" + id)
                .then().log().all()
                .extract();
    }

    /*
     * When 쿠폰번호 삭제요청을 하면
     * Then 쿠폰이 삭제된다.
     * */
    @Test
    void deleteCoupon() {
        int createCouponNumber = 6;
        int deleteCouponNumber = 3;
        createCoupon(createCouponNumber);

        given().log().all()
                .when().delete("/coupons/" + deleteCouponNumber)
                .then().log().all()
                .extract();

        ExtractableResponse<Response> couponResponse = findCoupon(deleteCouponNumber);

        assertThat(couponResponse.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
