package com.noose.storemanager.coupon.domain;

public enum CouponStatus {
    AVAILABLE("사용 가능한 쿠폰"),
    USED("사용된 쿠폰"),
    EXPIRED("만료된 쿠폰");

    final String description;

    CouponStatus(String description) {
        this.description = description;
    }
}
