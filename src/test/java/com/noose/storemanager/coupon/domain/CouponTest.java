package com.noose.storemanager.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[도메인] 쿠폰 기능")
class CouponTest {

    @DisplayName("쿠폰 이름 유효성 체크")
    @ParameterizedTest(name = "입력된 쿠폰 이름 길이: {0}")
    @ValueSource(ints = {1, 11})
    void couponNameException(int length) {
        assertThatThrownBy(() -> new Coupon("N".repeat(length), ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 설명이 30 글자를 초과하는 경우 예외가 발생한다.")
    @Test
    void couponDescriptionException() {
        assertThatThrownBy(() -> new Coupon("쿠폰", "D".repeat(31)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
