package com.noose.storemanager.repository;

import com.noose.storemanager.domain.admin.AdminEntity;
import com.noose.storemanager.domain.base.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByMemberId(String memberId);
    Optional<AdminEntity> findByPhoneNumber(PhoneNumber phoneNumber);
}
