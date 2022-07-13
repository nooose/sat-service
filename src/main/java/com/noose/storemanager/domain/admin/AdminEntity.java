package com.noose.storemanager.domain.admin;

import com.noose.storemanager.domain.member.MemberBaseEntity;
import com.noose.storemanager.domain.type.AdminRole;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "ADMIN")
public class AdminEntity extends MemberBaseEntity {
    @Id @GeneratedValue
    @Column(name = "ADMIN_ID")
    private long id;
    @Enumerated(EnumType.STRING)
    private AdminRole role;

    public AdminEntity(String memberId, String password, String name, String phoneNumber, AdminRole role) {
        super(memberId, password, name, phoneNumber);
        this.role = role;
    }

    public boolean isSuperAdmin() {
        return this.role.equals(AdminRole.SUPER);
    }
}
