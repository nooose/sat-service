package com.noose.storemanager.service;

import com.noose.storemanager.domain.base.MemberBaseEntity;
import com.noose.storemanager.domain.base.PhoneNumber;
import com.noose.storemanager.domain.ex.IllegalPhoneNumber;
import com.noose.storemanager.domain.user.UserEntity;
import com.noose.storemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        return userRepository.findByPhoneNumber(member.getPhoneNumber())
                .isPresent();
    }

    public List<UserEntity> findUsers(String query) {
        if (query == null) {
            return userRepository.findAll();
        }

        if (PhoneNumber.isValidPhoneNumber(query)) {
            return userRepository.findByPhoneNumber(new PhoneNumber(query))
                    .stream()
                    .collect(Collectors.toList());
        }

        return findUsersByName(query);
    }

    private List<UserEntity> findUsersByName(String name) {
        return userRepository.findByNameContains(name);
    }

    public UserEntity findOne(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateUser(Long userId, String name, String phoneNumber) {
        UserEntity findUser = findOne(userId);
        findUser.updateUser(name, phoneNumber);
    }
}
