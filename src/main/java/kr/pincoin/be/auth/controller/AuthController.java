package kr.pincoin.be.auth.controller;

import kr.pincoin.be.auth.domain.Group;
import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.auth.service.GroupService;
import kr.pincoin.be.auth.service.PermissionService;
import kr.pincoin.be.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {
    private final UserService userService;
    private final GroupService groupService;
    private final PermissionService permissionService;

    public AuthController(UserService userService, GroupService groupService, PermissionService permissionService) {
        this.userService = userService;
        this.groupService = groupService;
        this.permissionService = permissionService;
    }

    @GetMapping("")
    public String Home() {
        return "Auth controller";
    }

    // list, create, update, delete

    @GetMapping("/users")
    public Mono<ResponseEntity<List<User>>> UserList() {
        return userService.listUsers()
                .flatMap(response -> Mono.just(ResponseEntity.ok().body(response)));
    }

    @GetMapping("/groups")
    public Mono<ResponseEntity<List<Group>>> GroupList() {
        return groupService.listGroups()
                .flatMap(response -> {
                    return Mono.just(ResponseEntity.ok().body(response)); })
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }
}
