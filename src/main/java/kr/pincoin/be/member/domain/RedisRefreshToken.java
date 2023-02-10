package kr.pincoin.be.member.domain;

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
    @Indexed
    private final Long userId;
    @Indexed // 검색 가능 색인 필드
    private final String username;
    private final boolean active;

    public RedisRefreshToken(Long userId, String username, boolean active) {
        this.userId = userId;
        this.username = username;
        this.active = active;
    }

    public RedisRefreshToken issueRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

        this.expiresIn = LocalDateTime.now().plus(Duration.of(REFRESH_TOKEN_EXPIRES_IN, ChronoUnit.SECONDS));

        return this;
    }
}
