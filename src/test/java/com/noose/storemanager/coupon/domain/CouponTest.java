package com.noose.storemanager.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[도메인] 쿠폰 기능")
class CouponTest {

    @DisplayName("쿠폰의 이름은 2 글자 미만이거나 10 글자를 초과하는 경우 예외가 발생한다.")
    @ParameterizedTest(name = "쿠폰 이름 글자 수 : {0}")
    @ValueSource(ints = {1, 11, 14, 30, 0})
    void couponNameException(int numberOfCouponName) {
        assertThatThrownBy(() -> new Coupon(repeatCharacter(numberOfCouponName), ""))
                .isInstanceOf(IllegalArgumentException.class);
    }



    @DisplayName("쿠폰 설명이 30 글자를 초과하는 경우 예외가 발생한다.")
    @Test
    void couponDescriptionException() {
        assertThatThrownBy(() -> new Coupon("쿠폰", repeatCharacter(31)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static String repeatCharacter(int characterLength) {
        return "a".repeat(characterLength);
    }
}
