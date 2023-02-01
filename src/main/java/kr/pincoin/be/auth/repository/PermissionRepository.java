package kr.pincoin.be.auth.repository;

import kr.pincoin.be.auth.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
