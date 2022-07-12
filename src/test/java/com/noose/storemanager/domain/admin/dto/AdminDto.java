package com.noose.storemanager.domain.admin.dto;

import lombok.Data;

@Data
public class AdminDto {
    private String userId;
    private String password;
    private String name;
    private String phoneNumber;
}
