package kr.pincoin.be.auth.service;

import kr.pincoin.be.auth.domain.Permission;
import kr.pincoin.be.auth.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public Mono<List<Permission>> listUsers() {
        List<Permission> permissions = permissionRepository.findAll();
        if (permissions.isEmpty()) {
            return Mono.empty();
        }
        return Mono.just(permissions);
    }
}
