package kr.pincoin.be.auth.repository;

import kr.pincoin.be.auth.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Override
    List<Permission> findAll();
}
