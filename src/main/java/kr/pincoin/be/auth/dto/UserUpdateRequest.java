package kr.pincoin.be.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {
    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    private String firstName;

    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    private String lastName;

    @NotNull(message = "필수 입력 필드")
    @Email(message = "올바르지 않은 이메일 주소 형식")
    private String email;

    public UserUpdateRequest(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
