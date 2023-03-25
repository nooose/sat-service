package com.noose.storemanager.coupon.application.dto.request;

import com.noose.storemanager.coupon.domain.Coupon;

public record CouponRequest(
    String name,
    String description
) {
}
