package com.noose.storemanager.coupon.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    private void createCoupon(int createCouponNumber) {
        for (int i = 0; i < createCouponNumber; i++) {
            Map<String, String> params = new HashMap<>();
            params.put("name", "쿠폰");
            params.put("description", "테스트");
            ExtractableResponse<Response> response = given().log().all()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
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

        ExtractableResponse<Response> response = findAllCoupon();

        assertAll(() -> {
            assertThat(response.jsonPath().getList("name").size()).isEqualTo(createCouponNumber);
            assertThat(response.jsonPath().getList("description").size()).isEqualTo(createCouponNumber);
        });
    }

    private ExtractableResponse<Response> findAllCoupon() {
        return given().log().all()
                .when().get("/coupons")
                .then().log().all()
                .extract();
    }


    /*
     * When 쿠폰번호를 조회하면
     * Then 쿠폰번호에 해당하는 쿠폰을 확인할 수 있다.
     * */
    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource({"name,쿠폰", "description,테스트"})
    void showCouponOfOne(String path, String expected) {
        int createCouponNumber = 6;
        createCoupon(createCouponNumber);

        ExtractableResponse<Response> response = given().log().all()
                .when().get("/coupons/2")
                .then().log().all()
                .extract();

        assertThat(response.jsonPath().getString(path)).isEqualTo(expected);
    }

    /*
     * When 쿠폰번호 삭제요청을 하면
     * Then 쿠폰이 삭제된다.
     * */
    @ParameterizedTest
    @CsvSource({"name,10", "description,10"})
    void deleteCoupon(String path, int numberOfCoupon) {
        createCoupon(numberOfCoupon);

        given().log().all()
                .when().delete("/coupons/3")
                .then().log().all()
                .extract();

        ExtractableResponse<Response> coupons = findAllCoupon();

        assertThat(coupons.jsonPath().getList(path).size()).isEqualTo(--numberOfCoupon);
    }
}
