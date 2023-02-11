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
    @NotNull
    @NotBlank
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;

    public UserChangePasswordRequest(String password) {
        this.password = password;
    }
}
