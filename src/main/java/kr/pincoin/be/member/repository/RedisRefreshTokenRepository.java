package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.RedisRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshToken, Long> {
    // Redis는 JpaRepository가 아닌 CrudRepository만 사용 가능
    List<RedisRefreshToken> findByUserId(Long userId);

    Optional<RedisRefreshToken> findByRefreshToken(String refreshToken);
}
