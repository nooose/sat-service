package com.noose.storemanager.web.dto;

import com.noose.storemanager.domain.admin.AdminEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RequestAdminForm {
    @NotEmpty(message = "아이디는 필수 입니다.")
    @Length(min = 4, max = 15, message = "4자리에서 15자리 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "영문만 가능합니다.")
    private String memberId;
    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Length(min = 4, max = 15, message = "4자리에서 15자리 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z\\d]*$", message = "영문 + 숫자만 가능합니다.")
    private String password;
    @NotEmpty(message = "이름은 필수 입니다.")
    @Length(min = 2, max = 5, message = "올바른 이름을 입력해주세요.")
    private String name;
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?(\\d{3,4})-?(\\d{4})$", message = "올바른 휴대폰 번호를 입력해주세요.")
    @NotEmpty(message = "휴대폰 번호는 필수 입니다.")
    private String phoneNumber;

    public AdminEntity toEntity() {
        return new AdminEntity(this.memberId, this.password, this.name, this.phoneNumber);
    }
}
