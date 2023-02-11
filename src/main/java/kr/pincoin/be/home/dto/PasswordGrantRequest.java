package kr.pincoin.be.home.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordGrantRequest {
    // Password Grant : 3자 서버 로그인 처리에 있어서는 권장하지 않음
    // https://www.oauth.com/oauth2-servers/access-tokens/password-grant/
    @JsonProperty("grant_type")
    @NotNull(message = "필수 입력 필드")
    @Pattern(regexp = "password", flags = Pattern.Flag.CASE_INSENSITIVE, message = "잘못된 인가 요청")
    private String grantType;

    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    private String username;

    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    private String password;

    private String scope;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    public PasswordGrantRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
