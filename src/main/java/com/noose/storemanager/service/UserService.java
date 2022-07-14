package com.noose.storemanager.service;

import com.noose.storemanager.domain.ex.IllegalPhoneNumber;
import com.noose.storemanager.domain.base.MemberBaseEntity;
import com.noose.storemanager.domain.user.UserEntity;
import com.noose.storemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void joinUser(UserEntity user) {
        if (isDuplicatedPhoneNumber(user)) {
            throw new IllegalPhoneNumber();
        }

        userRepository.save(user);
    }


    private boolean isDuplicatedPhoneNumber(MemberBaseEntity member) {
        return userRepository.findByPhoneNumber(member.getPhoneNumber()).isPresent();
    }

    public List<UserEntity> findUsers() {
        return userRepository.findAll();
    }
}
