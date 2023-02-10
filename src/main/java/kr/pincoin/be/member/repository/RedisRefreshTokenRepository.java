package kr.pincoin.be.member.repository;

import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.member.domain.RedisRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshToken, Long> {
    // Redis는 JpaRepository가 아닌 CrudRepository만 사용 가능
    Optional<RedisRefreshToken> findByUser(@Param("user") User user);

    Optional<RedisRefreshToken> findByRefreshToken(String refreshToken);
}
