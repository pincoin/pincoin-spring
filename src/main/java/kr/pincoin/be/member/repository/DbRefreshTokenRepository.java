package kr.pincoin.be.member.repository;

import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.member.domain.DbRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DbRefreshTokenRepository extends CrudRepository<DbRefreshToken, Long> {
    Optional<DbRefreshToken> findByUser(@Param("user") User user);
}
