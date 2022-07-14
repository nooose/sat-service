package com.noose.storemanager.domain.admin;

import com.noose.storemanager.domain.base.MemberBaseEntity;
import com.noose.storemanager.domain.base.PhoneNumber;
import com.noose.storemanager.domain.type.AdminRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "ADMIN")
public class AdminEntity extends MemberBaseEntity {

    @Id @GeneratedValue
    @Column(name = "ADMIN_ID")
    @Getter
    private long id;

    @Getter
    private String memberId;
    private String password;
    @Enumerated(EnumType.STRING)
    @Getter
    private AdminRole role;

    public AdminEntity(String memberId, String password, String name, String phoneNumber, AdminRole role) {
        this.name = name;
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.memberId = memberId;
        this.password = password;
        this.role = role;
    }

    public AdminEntity(String memberId, String password, String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.memberId = memberId;
        this.password = password;
        this.role = AdminRole.GENERAL;
    }

    public boolean isSuperAdmin() {
        return this.role.equals(AdminRole.SUPER);
    }
}
