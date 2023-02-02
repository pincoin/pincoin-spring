package kr.pincoin.be.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenResponse {
    // 액세스 토큰 응답
    // https://www.oauth.com/oauth2-servers/access-tokens/access-token-response/

    @JsonProperty("access_token")
    @NotNull
    @NotBlank
    private String accessToken;

    @JsonProperty("token_type")
    @NotNull
    @NotBlank
    private String tokenType;

    @JsonProperty("expires_in")
    @NotNull
    @NotBlank
    private String expiresIn;

    @JsonProperty("refresh_token")
    @NotNull
    @NotBlank
    private String refreshToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String scope;

    public AccessTokenResponse(String accessToken,
                               String tokenType,
                               String expiresIn,
                               String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }
}
