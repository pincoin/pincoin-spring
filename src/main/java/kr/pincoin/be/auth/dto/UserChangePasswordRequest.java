package kr.pincoin.be.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static kr.pincoin.be.auth.dto.UserCreateRequest.PASSWORD_PATTERN;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChangePasswordRequest {
    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    @Pattern(regexp = PASSWORD_PATTERN, message = "대문자, 소문자, 숫자, 특수문자를 각각 하나 이상 포함한 8자 이상")
    private String password;

    public UserChangePasswordRequest(String password) {
        this.password = password;
    }
}
