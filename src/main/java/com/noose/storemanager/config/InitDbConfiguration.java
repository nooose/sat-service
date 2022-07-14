package com.noose.storemanager.config;

import com.noose.storemanager.domain.admin.AdminEntity;
import com.noose.storemanager.domain.type.AdminRole;
import com.noose.storemanager.domain.user.UserEntity;
import com.noose.storemanager.repository.AdminRepository;
import com.noose.storemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Profile("default")
@RequiredArgsConstructor
public class InitDbConfiguration {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initAdmin();
        initService.initUser();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final AdminRepository adminRepository;
        private final UserRepository userRepository;


        public void initAdmin(){
            AdminEntity adminA = new AdminEntity("yesung", "8163", "성주성", "01030631100", AdminRole.SUPER);
            AdminEntity adminB = new AdminEntity("noose", "951104", "성준혁", "01072361800", AdminRole.SUPER);
            AdminEntity adminC = new AdminEntity("test", "test", "테스트", "01012345678", AdminRole.GENERAL);
            adminRepository.save(adminA);
            adminRepository.save(adminB);
            adminRepository.save(adminC);
        }

        public void initUser() {
            UserEntity userA = new UserEntity("홍길동", "010-1111-2323");
            UserEntity userB = new UserEntity("권규정", "010-3333-4444");
            UserEntity userC = new UserEntity("김영철", "010-5678-2323");

            userRepository.save(userA);
            userRepository.save(userB);
            userRepository.save(userC);
        }
    }
}
