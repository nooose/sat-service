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
        validateName(name.strip());
        validateDescription(description.strip());

        this.name = name.strip();
        this.description = description.strip();
    }

    private void validateName(String name) {
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰 이름은 %d글자 미만이거나 %d글자를 초과할 수 없습니다.",NAME_MIN_LENGTH, NAME_MAX_LENGTH));
        }

    }

    private void validateDescription(String description) {
        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰 설명은 %d글자를 초과할 수 없습니다.", DESCRIPTION_MAX_LENGTH));
        }
    }
}
