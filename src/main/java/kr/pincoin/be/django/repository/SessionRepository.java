package kr.pincoin.be.django.repository;

import kr.pincoin.be.django.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
