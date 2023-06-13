package com.sat.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Name {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 10;
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("^[a-zA-Z가-힣0-9]+$");

    @Column(name = "member_name")
    private String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("이름은 %d글자 이상 %d글자 이하이어야 합니다.", MIN_LENGTH, MAX_LENGTH));
        }

        if (!SPECIAL_CHARS_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("특수문자는 포함할 수 없습니다.");
        }
    }
}
