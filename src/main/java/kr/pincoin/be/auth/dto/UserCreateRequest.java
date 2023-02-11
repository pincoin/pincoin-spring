package kr.pincoin.be.auth.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {
    // 대소문자와 숫자, 점(.), 밑줄(_), 하이픈(-)으로 구성
    // 점, 밑줄, 하이픈으로 시작하거나 끝나지 않음
    // 점, 밑줄, 하이픈은 연속으로 사용할 수 없음
    // 길이는 3~32자
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,32}[a-zA-Z0-9]$";

    // 대문자 최소 1개 이상 (?=.*?[A-Z])
    // 소문자 최소 1개 이상 (?=.*?[a-z])
    // 숫자 최소 1개 이상 (?=.*?[0-9])
    // 특수문자 1개 이상 (?=.*?[#?!@$%^&*-])
    // 8자 이상 .{8,} (with the anchors)
    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    @NotNull
    @NotBlank
    @Pattern(regexp = USERNAME_PATTERN)
    private String username;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;

    public UserCreateRequest(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
