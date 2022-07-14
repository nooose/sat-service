package com.noose.storemanager.repository;

import com.noose.storemanager.domain.base.PhoneNumber;
import com.noose.storemanager.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(PhoneNumber phoneNumber);
}
