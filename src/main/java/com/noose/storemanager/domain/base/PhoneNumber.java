package com.noose.storemanager.domain.base;

import com.noose.storemanager.domain.ex.IllegalPhoneNumber;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode
@NoArgsConstructor
public class PhoneNumber {
    public static Pattern PATTERN = Pattern.compile("^01([0|1|6|7|8|9])-?(\\d{3,4})-?(\\d{4})$");

    @Getter
    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalPhoneNumber("올바른 휴대폰 번호를 입력해주세요.");
        }

        this.phoneNumber = phoneNumber.replaceAll("-","");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }
}
