package com.noose.storemanager.domain.admin.dto;

import lombok.Data;

@Data
public class RequestAdminDto {
    private String memberId;
    private String password;
    private String name;
    private String phoneNumber;
}
