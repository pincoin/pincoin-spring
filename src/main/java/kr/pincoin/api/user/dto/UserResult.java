package kr.pincoin.api.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserResult {
    private Long id;

    private String username;

    private String password;

    private Boolean active;

    public UserResult(Long id,
                      String password,
                      String username,
                      Boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
    }
}