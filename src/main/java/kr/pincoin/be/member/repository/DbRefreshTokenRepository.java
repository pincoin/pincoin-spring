package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.DbRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DbRefreshTokenRepository extends JpaRepository<DbRefreshToken, Long> {
    @Query(value = "SELECT rt" +
            " FROM DbRefreshToken rt" +
            " JOIN FETCH User u" +
            " ON u.id = rt.user.id " +
            " WHERE u.username = :username" +
            " AND u.active = true")
    Optional<DbRefreshToken> findByUsername(@Param("username") String username);
}
