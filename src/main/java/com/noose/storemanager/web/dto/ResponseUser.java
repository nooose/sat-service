package com.noose.storemanager.web.dto;

import com.noose.storemanager.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseUser {
    private long id;
    private String name;
    private String phoneNumber;

    public static ResponseUser createFrom(UserEntity user) {
        return ResponseUser.builder()
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumberString())
                .build();
    }
}
