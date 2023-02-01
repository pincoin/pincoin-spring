package kr.pincoin.be.auth.controller;

import kr.pincoin.be.auth.domain.Group;
import kr.pincoin.be.auth.domain.Permission;
import kr.pincoin.be.auth.dto.UserResponse;
import kr.pincoin.be.auth.service.GroupService;
import kr.pincoin.be.auth.service.PermissionService;
import kr.pincoin.be.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> UserList() {
        List<UserResponse> users = userService.listUsers()
                .stream()
                .map(user -> new UserResponse(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.isSuperuser(),
                        user.isStaff(),
                        user.isActive(),
                        user.getLastLogin(),
                        user.getDateJoined()))
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> UserDetail(@PathVariable Long userId) {
        return userService
                .getUser(userId)
                .map((user) -> ResponseEntity.ok().body(
                        new UserResponse(
                                user.getUsername(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.isSuperuser(),
                                user.isStaff(),
                                user.isActive(),
                                user.getLastLogin(),
                                user.getDateJoined()
                        )))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> GroupList() {
        List<Group> groups = groupService.listGroups();

        if (groups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(groups);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> PermissionList() {
        List<Permission> permissions = permissionService.listPermissions();

        if (permissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(permissions);
    }


    // createUser
    // updateUser
    // deleteUser
    // listGroupsOfUser
    // listPermissionsOfUser

    // listContentTypes
    // getContentType
    // createContentType
    // updateContentType
    // deleteContentType
    // listPermissionsOfContentType
    // createPermissionOfContentType
    // updatePermissionOfContentType
    // deletePermissionOfContentType

    // listGroups
    // getGroup
    // createGroup
    // updateGroup
    // deleteGroup
    // listUsersOfGroup
    // listPermissionsOfGroup
    // createUserOfGroup
    // deleteUserOfGroup

    // listPermissions
    // getPermissions
    // listUsersOfPermission
    // listGroupsOfPermission
    // createPermissionOfUser
    // deletePermissionOfUser
    // createPermissionOfGroup
    // deletePermissionOfGroup
    // listContentTypesOfPermission
}
