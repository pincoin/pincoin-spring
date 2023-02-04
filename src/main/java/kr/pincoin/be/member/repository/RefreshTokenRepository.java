package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query(value = "SELECT rt" +
            " FROM RefreshToken rt" +
            " JOIN FETCH User u" +
            " ON u.id = rt.user.id " +
            " WHERE u.username = :username" +
            " AND u.active = true")
    Optional<RefreshToken> findByUsername(@Param("username") String username);
}
