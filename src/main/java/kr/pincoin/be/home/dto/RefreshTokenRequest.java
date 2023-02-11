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
public class RefreshTokenRequest {
    // Refresh Token : 3자 서버 로그인 처리에 있어서는 권장하지 않음
    // https://www.oauth.com/oauth2-servers/making-authenticated-requests/refreshing-an-access-token/
    @JsonProperty("grant_type")
    @NotNull(message = "필수 입력 필드")
    @Pattern(regexp = "refresh_token", flags = Pattern.Flag.CASE_INSENSITIVE, message = "잘못된 인가 요청")
    private String grantType;

    @JsonProperty("refresh_token")
    @NotNull(message = "필수 입력 필드")
    @NotBlank(message = "필수 입력 필드")
    private String refreshToken;

    private String scope;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
