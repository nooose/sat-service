package com.noose.storemanager.coupon.domain;

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
            throw new IllegalArgumentException("쿠폰의 이름은 2글자 초과하고 10글자 미만이어야 하며 쿠폰 설명은 30글자를 초과해선 안됩니다.");
        }

        this.name = name;
        this.description = description;
    }

    private static boolean checkCharacterLength(String name, String description) {
        return name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH || description.length() > DESCRIPTION_MAX_LENGTH;
    }
}
