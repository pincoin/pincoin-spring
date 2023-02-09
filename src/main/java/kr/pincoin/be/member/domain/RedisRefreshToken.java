package kr.pincoin.be.member.domain;

import kr.pincoin.be.auth.domain.User;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;


@Getter
@RedisHash(value = "refresh_token")
public class RedisRefreshToken {
    @Id
    private Long id;
    private String refreshToken;
    private LocalDateTime expiresIn;
    private User user;

    public RedisRefreshToken(Long id, String refreshToken, LocalDateTime expiresIn, User user) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.user = user;
    }
}
