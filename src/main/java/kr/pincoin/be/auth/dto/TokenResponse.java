package kr.pincoin.be.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {
    private String token;

    private LocalDateTime expireDate;

    public TokenResponse(String token, LocalDateTime expireDate) {
        this.token = token;
        this.expireDate = expireDate;
    }
}
