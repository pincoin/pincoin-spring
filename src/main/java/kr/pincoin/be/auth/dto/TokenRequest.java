package kr.pincoin.be.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRequest {
    private String username;

    private String password;

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
