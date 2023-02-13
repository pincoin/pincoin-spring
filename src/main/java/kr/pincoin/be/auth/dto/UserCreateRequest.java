package kr.pincoin.be.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {
    // 알파벳, 숫자, 점(.), 밑줄(_), 하이픈(-) 사용 가능
    // 점, 밑줄, 하이픈으로 시작하거나 끝나지 않음
    // 점, 밑줄, 하이픈은 연속으로 사용할 수 없음 (이모티콘 형식 사용 불가)
    // 길이는 3~32자
    // Django: https://github.com/django/django/blob/main/django/contrib/auth/validators.py
    // regex = r"^[\w.@+-]+\Z" (문자, 숫자, @ . + - _)
    public static final String USERNAME_PATTERN = "^(?=.{3,32}$)(?![._-])(?!.*[._-]{2})[a-zA-Z0-9._-]+(?<![_.])$";

    // 대문자 최소 1개 이상 (?=.*?[A-Z])
    // 소문자 최소 1개 이상 (?=.*?[a-z])
    // 숫자 최소 1개 이상 (?=.*?[0-9])
    // 특수문자 1개 이상 (?=.*?[#?!@$%^&*-])
    // 8자 이상 .{8,} (with the anchors)
    // Django: https://github.com/django/django/blob/main/django/contrib/auth/password_validation.py
    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    @Pattern(regexp = USERNAME_PATTERN, message = "알파벳, 숫자, 점(.), 밑줄(_), 하이픈(-)으로 4~32자, 이모티콘 아이디 사용불가")
    private String username;

    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    private String firstName;

    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    private String lastName;

    @NotNull(message = "필수 입력 필드")
    @Email(message = "올바르지 않은 이메일 주소 형식")
    private String email;

    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    @Pattern(regexp = PASSWORD_PATTERN, message = "대문자, 소문자, 숫자, 특수문자를 각각 하나 이상 포함한 8자 이상")
    private String password;

    public UserCreateRequest(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
