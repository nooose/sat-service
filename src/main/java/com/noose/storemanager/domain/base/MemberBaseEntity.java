package com.noose.storemanager.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class MemberBaseEntity extends BaseEntity {
    protected String name;
    @Embedded
    protected PhoneNumber phoneNumber;

    public String getPhoneNumberString() {
        return this.getPhoneNumber().getPhoneNumber();
    }
}
