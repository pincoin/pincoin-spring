package kr.pincoin.be.member.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static kr.pincoin.be.auth.jwt.TokenProvider.REFRESH_TOKEN_EXPIRES_IN;


@Getter
@RedisHash(value = "refresh_token", timeToLive = 5)
public class RedisRefreshToken {
    @Id
    private Long id;
    @Indexed // 검색 가능 색인 필드
    private String refreshToken;
    private LocalDateTime expiresIn;

    // @TimeToLive 또는 @RedisHash(timeToLive = 1) 애노테이션으로 TTL 설정 시 데이터 정합성 오류 발생
    // TTL 시간이 지나면 저장된 데이터는 제거되지만, key 목록을 관리하는 set의 element는 제거되지 않음
    @TimeToLive
    private final Long ttl;
    @Indexed
    private final Long userId;
    private final String username;
    private final boolean active;

    // 데이터 간의 관계가 있거나 데이터의 값으로 검색이 필요하다면 관계형 데이터베이스 사용
    // 영속성 보장이 필요한 데이터는 되도록 Redis가 아닌 on-disk 기반의 저장소에 기록
    // DEL collection과 같은 O(n) 시간복잡도의 명령으로 운영 환경 Redis가 멈추지 않도록 조심

    public RedisRefreshToken(Long userId, String username, boolean active, Long ttl) {
        this.userId = userId;
        this.username = username;
        this.active = active;
        this.ttl = ttl;
    }

    public RedisRefreshToken issueRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

        this.expiresIn = LocalDateTime.now().plus(Duration.of(REFRESH_TOKEN_EXPIRES_IN, ChronoUnit.SECONDS));

        return this;
    }
}
