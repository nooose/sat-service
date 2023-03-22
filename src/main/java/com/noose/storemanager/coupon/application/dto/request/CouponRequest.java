package com.noose.storemanager.coupon.application.dto.request;

import com.noose.storemanager.coupon.domain.Coupon;

public record CouponRequest(
    String name,
    String description
) {
    public Coupon of(CouponRequest request) {
        return new Coupon(request.name(), request.description());
    }
}
