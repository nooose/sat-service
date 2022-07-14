package com.noose.storemanager.web.dto;

import com.noose.storemanager.domain.user.UserEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RequestUserForm {
    @NotEmpty(message = "이름은 필수 입니다.")
    @Length(min = 2, max = 5, message = "올바른 이름을 입력해주세요.")
    private String name;
    @NotEmpty(message = "휴대폰 번호 필수 입니다.")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?(\\d{3,4})-?(\\d{4})$", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

    public UserEntity toEntity() {
        return new UserEntity(this.name, this.phoneNumber);
    }
}
