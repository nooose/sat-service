package com.noose.storemanager.coupon.application.dto.response;

import com.noose.storemanager.coupon.domain.Coupon;

public record CouponResponse(
        Long id,
        String name,
        String description
) {
    public static CouponResponse of(Coupon entity) {
        return new CouponResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
