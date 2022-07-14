package com.noose.storemanager.domain.user;

import com.noose.storemanager.domain.base.MemberBaseEntity;
import com.noose.storemanager.domain.base.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
public class UserEntity extends MemberBaseEntity {
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    @Getter
    private long id;

    public UserEntity(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }
}
