package kr.pincoin.be.member.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import kr.pincoin.be.auth.domain.Group;
import kr.pincoin.be.auth.domain.Permission;
import kr.pincoin.be.member.dto.UserCreateRequest;
import kr.pincoin.be.member.dto.UserResponse;
import kr.pincoin.be.auth.service.GroupService;
import kr.pincoin.be.auth.service.PermissionService;
import kr.pincoin.be.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
@Slf4j
public class MemberController {
    private final UserService userService;
    private final GroupService groupService;
    private final PermissionService permissionService;

    public MemberController(UserService userService,
                            GroupService groupService,
                            PermissionService permissionService) {
        this.userService = userService;
        this.groupService = groupService;
        this.permissionService = permissionService;
    }

    @GetMapping("")
    public String
    Home() {
        return "Member controller";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>>
    UserList() {
        List<UserResponse> users = userService.listActiveUsers()
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

    @GetMapping("/users/{username}")
    public ResponseEntity<UserResponse>
    UserDetail(@PathVariable String username) {
        return userService
                .getActiveUser(username)
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

    @GetMapping("/staffs")
    public ResponseEntity<List<UserResponse>>
    StaffUserList() {
        List<UserResponse> users = userService.listStaffUsers()
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

    @GetMapping("/staffs/{username}")
    public ResponseEntity<UserResponse>
    StaffUserDetail(@PathVariable String username) {
        return userService
                .getStaffUser(username)
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

    @GetMapping("/superusers")
    public ResponseEntity<List<UserResponse>>
    SuperUserList() {
        List<UserResponse> users = userService.listSuperUsers()
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

    @GetMapping("/superusers/{username}")
    public ResponseEntity<UserResponse>
    StaffDetail(@PathVariable String username) {
        return userService
                .getSuperUser(username)
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
    public ResponseEntity<List<Group>>
    GroupList() {
        List<Group> groups = groupService.listGroups();

        if (groups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(groups);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>>
    PermissionList() {
        List<Permission> permissions = permissionService.listPermissions();

        if (permissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(permissions);
    }

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

    // createGroup
    // updateGroup
    // deleteGroup
    // listUsersOfGroup
    // listPermissionsOfGroup
    // createUserOfGroup
    // deleteUserOfGroup

    // listUsersOfPermission
    // listGroupsOfPermission
    // createPermissionOfUser
    // deletePermissionOfUser
    // createPermissionOfGroup
    // deletePermissionOfGroup
    // listContentTypesOfPermission
}
