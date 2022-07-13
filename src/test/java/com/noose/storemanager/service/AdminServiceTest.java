package com.noose.storemanager.service;

import com.noose.storemanager.domain.admin.AdminEntity;
import com.noose.storemanager.domain.type.AdminRole;
import com.noose.storemanager.domain.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @Test
    void 관리자_가입() {
        AdminEntity admin = new AdminEntity("test", "test", "테스트", "010-1234-5678", AdminRole.GENERAL);
        adminService.joinAdmin(admin);

        AdminEntity findAdmin = adminService.findAdminById(1L).get();
        log.info("createdAt={}", findAdmin.getCreatedAt());

        assertThat(findAdmin.getName()).isEqualTo("테스트");
    }

    @Test
    void 관리자_가입_중복된_아이디_예외발생() {
        AdminEntity adminA = new AdminEntity("test", "test", "테스트", "010-1234-5678", AdminRole.GENERAL);
        adminService.joinAdmin(adminA);

        AdminEntity adminB = new AdminEntity("test", "test", "테스트", "010-1234-5679", AdminRole.GENERAL);
        assertThatThrownBy(() -> adminService.joinAdmin(adminB)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 관리자_가입_중복된_휴대폰번호_예외발생() {
        AdminEntity adminA = new AdminEntity("testA", "test", "테스트", "010-1234-5678", AdminRole.GENERAL);
        adminService.joinAdmin(adminA);

        AdminEntity adminB = new AdminEntity("testB", "test", "테스트", "010-1234-5678", AdminRole.GENERAL);
        assertThatThrownBy(() -> adminService.joinAdmin(adminB)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 일반유저_회원_가입() {
        UserEntity user = new UserEntity("test", "test", "유저", "010-0101-0000");
        adminService.joinUser(user);

        assertThat(adminService.findUserById(1L).get().getName()).isEqualTo("유저");
    }
}