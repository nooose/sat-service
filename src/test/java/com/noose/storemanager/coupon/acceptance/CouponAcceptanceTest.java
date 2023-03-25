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

    Long 일반쿠폰;
    Long 할인쿠폰;
    Long 무료쿠폰;

    // Given: 쿠폰을 생성하고
    @BeforeEach
    public void setUp() {
        super.setUp();
        일반쿠폰 = 쿠폰_생성_요청("일반", "테스트");
        할인쿠폰 = 쿠폰_생성_요청("할인", "테스트");
        무료쿠폰 = 쿠폰_생성_요청("무료", "테스트");
    }

    /**
     * When 쿠폰 생성을 요청하면<p>
     * Then 생성된 쿠폰을 확인할 수 있다.
     */
    @DisplayName("쿠폰 생성")
    @Test
    void createCoupon() {
        var response = 쿠폰_조회(일반쿠폰);

        assertThat(response.jsonPath().getString("name")).isEqualTo("일반");
    }

    /**
     * When 쿠폰 목록을 조회하면<p>
     * Then 생성된 모든 쿠폰을 확인할 수 있다.
     */
    @DisplayName("쿠폰 목록 조회")
    @Test
    void showCoupons() {
        var response = 쿠폰_목록_조회_요청();

        assertThat(response.jsonPath().getList("id", Long.class))
                .containsExactly(일반쿠폰, 할인쿠폰, 무료쿠폰);
    }


    /**
     * Given 쿠폰을 생성하고<p>
     * When 쿠폰번호를 조회하면<p>
     * Then 쿠폰번호에 해당하는 쿠폰을 확인할 수 있다.
     */
    @DisplayName("쿠폰 단건 조회")
    @Test
    void showCouponOfOne() {
        var response = 쿠폰_조회(일반쿠폰);

        assertAll(() -> {
            assertThat(response.jsonPath().getString("name")).isEqualTo("일반");
            assertThat(response.jsonPath().getString("description")).isEqualTo("테스트");
        });
    }

    /**
     * When 특정 쿠폰을 삭제하면<p>
     * Then 쿠폰 목록에서 삭제된 쿠폰을 찾을 수 없다.
     */
    @DisplayName("쿠폰 삭제")
    @Test
    void deleteCoupon() {
        var deleteResponse = 쿠폰_삭제_요청(할인쿠폰);

        var 쿠폰_목록 = 쿠폰_목록_조회_요청();
        assertAll(() -> {
            assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
            assertThat(쿠폰_목록.jsonPath().getList("id", Long.class))
                    .containsExactly(일반쿠폰, 무료쿠폰);
        });
    }

    private Long 쿠폰_생성_요청(String name, String description) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("description", description);

        var response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/coupons")
                .then().log().all()
                .extract();

        return response.jsonPath().getLong("id");
    }

    private ExtractableResponse<Response> 쿠폰_목록_조회_요청() {
        return given().log().all()
                .when().get("/coupons")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 쿠폰_조회(Long id) {
        return given().log().all()
                .pathParam("couponId", id)
                .when().get("/coupons/{couponId}")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 쿠폰_삭제_요청(Long couponId) {
        return given().log().all()
                .when().delete("/coupons/" + couponId)
                .then().log().all()
                .extract();
    }
}
