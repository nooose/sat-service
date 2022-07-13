package com.noose.storemanager.service;

import com.noose.storemanager.domain.admin.AdminEntity;
import com.noose.storemanager.domain.member.MemberBaseEntity;
import com.noose.storemanager.domain.user.UserEntity;
import com.noose.storemanager.repository.AdminRepository;
import com.noose.storemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public void joinAdmin(AdminEntity admin) {
        checkValidateMember(admin);
        adminRepository.save(admin);
    }

    public void joinUser(UserEntity user) {
        checkValidateMember(user);
        userRepository.save(user);
    }

    private void checkValidateMember(MemberBaseEntity admin) {
        if (isDuplicatedUserId(admin)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }

        if (isDuplicatedPhoneNumber(admin)) {
            throw new IllegalArgumentException("중복된 휴대폰 번호입니다.");
        }
    }

    private boolean isDuplicatedUserId(MemberBaseEntity member) {
        return adminRepository.findByMemberId(member.getMemberId()).isPresent();
    }

    private boolean isDuplicatedPhoneNumber(MemberBaseEntity member) {
        return adminRepository.findByPhoneNumber(member.getPhoneNumber()).isPresent();
    }

    public Optional<AdminEntity> findAdminById(long id) {
        return adminRepository.findById(id);
    }

    public Optional<UserEntity> findUserById(long id) {
        return userRepository.findById(id);
    }
}
