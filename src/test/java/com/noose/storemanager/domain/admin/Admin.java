package com.noose.storemanager.domain.admin;

import com.noose.storemanager.domain.admin.dto.AdminDto;
import com.noose.storemanager.domain.type.AdminRole;

public class Admin {
    private long id;
    private String userId;
    private String password;
    private String name;
    private String phoneNumber;
    private AdminRole role;

    public Admin(long id, String userId, String password, String name, String phoneNumber, AdminRole role) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Admin(String userId, String password, String name, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = AdminRole.GENERAL;
    }

    public Admin(AdminDto requestAdmin) {
        this.userId = requestAdmin.getUserId();
        this.password = requestAdmin.getPassword();
        this.name = requestAdmin.getName();
        this.phoneNumber = requestAdmin.getPhoneNumber();
    }

    public Admin createNewAdmin(AdminDto requestAdmin) {
        if (!this.role.equals(AdminRole.SUPER)) {
            throw new IllegalStateException("");
        }

        return new Admin(requestAdmin);
    }
}
