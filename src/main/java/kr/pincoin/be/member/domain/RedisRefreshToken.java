package kr.pincoin.be.member.domain;

import kr.pincoin.be.auth.domain.User;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static kr.pincoin.be.auth.jwt.TokenProvider.REFRESH_TOKEN_EXPIRES_IN;


@Getter
@RedisHash(value = "refresh_token")
public class RedisRefreshToken {
    @Id
    private Long id;
    @Indexed // 검색 가능 색인 필드
    private String refreshToken;
    private LocalDateTime expiresIn;
    private final User user;

    public RedisRefreshToken(User user) {
        this.user = user;
    }

    public RedisRefreshToken issueRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

        this.expiresIn = LocalDateTime.now().plus(Duration.of(REFRESH_TOKEN_EXPIRES_IN, ChronoUnit.SECONDS));

        return this;
    }
}
