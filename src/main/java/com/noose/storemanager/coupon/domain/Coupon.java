package com.noose.storemanager.coupon.domain;

import com.noose.storemanager.coupon.application.dto.request.CouponRequest;
import com.noose.storemanager.coupon.application.dto.response.CouponResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public Coupon(String name, String description) {
        if (name.length() < 2 || name.length() > 10 || description.length() > 30) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.description = description;
    }

    public CouponResponse toResponse() {
        return new CouponResponse(id, name, description);
    }
}
