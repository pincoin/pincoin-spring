package kr.pincoin.be.member.repository;

import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.member.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUser(User user);
}
