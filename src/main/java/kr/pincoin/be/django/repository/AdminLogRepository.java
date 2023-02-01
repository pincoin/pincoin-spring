package kr.pincoin.be.django.repository;

import kr.pincoin.be.django.domain.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLogRepository extends JpaRepository<AdminLog, Long> {
}
