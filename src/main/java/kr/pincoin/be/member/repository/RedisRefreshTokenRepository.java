package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.RedisRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedisRefreshTokenRepository extends JpaRepository<RedisRefreshToken, Long> {
}
