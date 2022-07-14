package com.noose.storemanager.web.dto;

import com.noose.storemanager.domain.admin.AdminEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseAdmin {

    private long id;
    private String name;
    private String memberId;
    private String phoneNumber;
    private String role;

    public ResponseAdmin(long id, String name, String memberId, String phoneNumber, String role) {
        this.id = id;
        this.name = name;
        this.memberId = memberId;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static ResponseAdmin createFrom(AdminEntity admin) {
        return ResponseAdmin.builder()
                .id(admin.getId())
                .name(admin.getName())
                .memberId(admin.getMemberId())
                .phoneNumber(admin.getPhoneNumberString())
                .role(admin.getRole().toString())
                .build();
    }
}
