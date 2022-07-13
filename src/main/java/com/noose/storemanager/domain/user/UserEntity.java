package com.noose.storemanager.domain.user;

import com.noose.storemanager.domain.member.MemberBaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@NoArgsConstructor
public class UserEntity extends MemberBaseEntity {
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private long id;

    public UserEntity(String memberId, String password, String name, String phoneNumber) {
        super(memberId, password, name, phoneNumber);
    }
}
