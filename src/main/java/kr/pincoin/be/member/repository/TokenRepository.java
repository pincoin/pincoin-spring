package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
