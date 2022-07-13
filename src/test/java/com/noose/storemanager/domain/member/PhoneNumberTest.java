package com.noose.storemanager.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class PhoneNumberTest {

    @Test
    void 올바른_휴대폰_번호() {
        assertThat(new PhoneNumber("010-1234-5678")).isNotNull();
    }

    @Test
    void 올바르지않은_휴대폰_번호() {
        assertThatThrownBy(() -> new PhoneNumber("01-0123-4567")).isInstanceOf(IllegalArgumentException.class);
    }
}