package com.noose.storemanager.coupon.domain;

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

    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 10;
    private static final int DESCRIPTION_MAX_LENGTH = 30;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public Coupon(String name, String description) {
        if (checkCharacterLength(name, description)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.description = description;
    }

    private static boolean checkCharacterLength(String name, String description) {
        return name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH || description.length() > DESCRIPTION_MAX_LENGTH;
    }

    public CouponResponse toResponse() {
        return new CouponResponse(id, name, description);
    }
}
