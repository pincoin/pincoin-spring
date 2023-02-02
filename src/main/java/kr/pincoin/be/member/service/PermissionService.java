package kr.pincoin.be.member.service;

import kr.pincoin.be.auth.domain.Permission;
import kr.pincoin.be.auth.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public List<Permission> listPermissions() {
        return permissionRepository.findAll();
    }
}
