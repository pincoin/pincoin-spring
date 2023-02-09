package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.DbRefreshToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DbRefreshTokenRepository extends CrudRepository<DbRefreshToken, Long> {
    @Query(value = "SELECT rt" +
            " FROM DbRefreshToken rt" +
            " WHERE rt.user.id = :userId")
    Optional<DbRefreshToken> findById(@Param("userId") String userId);
}
