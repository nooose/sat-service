package com.noose.storemanager.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[도메인] 쿠폰 기능")
class CouponTest {

    @DisplayName("쿠폰의 이름은 2 글자 미만이거나 10 글자를 초과하는 경우 예외가 발생한다.")
    @Test
    void couponNameException() {
        assertThatThrownBy(() -> new Coupon("2", ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 설명이 30 글자를 초과하는 경우 예외가 발생한다.")
    @Test
    void couponDescriptionException() {
        assertThatThrownBy(() -> new Coupon("쿠폰", "가나다라마바사아자차카타파하아야어여오요우유으이30글자로올라가라올라가쭉쭉쭉올라가랏!"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
