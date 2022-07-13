package com.noose.storemanager.domain.member;

import com.noose.storemanager.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class MemberBaseEntity extends BaseEntity {
    @Column(length = 30)
    private String memberId;
    @Column(length = 30)
    private String password;
    @Column(length = 5)
    private String name;
    @Embedded
    @Column(length = 15)
    private PhoneNumber phoneNumber;

    public MemberBaseEntity(String memberId, String password, String name, String phoneNumber) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }
}
