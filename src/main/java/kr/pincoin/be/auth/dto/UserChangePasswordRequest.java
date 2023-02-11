package kr.pincoin.be.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChangePasswordRequest {
    @NotNull
    @NotBlank
    private String password;

    public UserChangePasswordRequest(String password) {
        this.password = password;
    }
}
