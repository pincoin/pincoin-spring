package kr.pincoin.be.auth.controller;

import jakarta.validation.Valid;
import kr.pincoin.be.auth.domain.Group;
import kr.pincoin.be.auth.domain.Permission;
import kr.pincoin.be.auth.dto.UserChangePasswordRequest;
import kr.pincoin.be.auth.dto.UserResponse;
import kr.pincoin.be.auth.dto.UserUpdateRequest;
import kr.pincoin.be.auth.service.GroupService;
import kr.pincoin.be.auth.service.PermissionService;
import kr.pincoin.be.auth.service.UserService;
import kr.pincoin.be.home.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    public AuthController(UserService userService,
                          GroupService groupService,
                          PermissionService permissionService) {
        this.userService = userService;
        this.groupService = groupService;
        this.permissionService = permissionService;
    }

    @GetMapping("")
    public String home() {
        return "Auth controller";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>>
    listUsers() {
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
            throw new ApiException(HttpStatus.NO_CONTENT,
                             "사용자 없음",
                             List.of("조회된 사용자가 없습니다."));
        }

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserResponse>
    getUser(@PathVariable String username) {
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
                .orElseThrow(() -> new ApiException(HttpStatus.NO_CONTENT,
                                                          "사용자 없음",
                                                          List.of("조회된 사용자가 없습니다.")));
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<UserResponse>
    updateUser(@PathVariable String username,
               @Valid @RequestBody UserUpdateRequest request) {
        log.debug(username);
        return null;
    }

    @PutMapping("/users/{username}/change-password")
    public ResponseEntity<UserResponse>
    changeUserPassword(@PathVariable String username,
                       @Valid @RequestBody UserChangePasswordRequest request) {
        log.debug(username);
        return null;
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<UserResponse>
    deleteUser(@PathVariable String username) {
        log.debug(username);
        return null;
    }

    @GetMapping("/staffs")
    public ResponseEntity<List<UserResponse>>
    listStaffUsers() {
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
            throw new ApiException(HttpStatus.NO_CONTENT,
                                   "스태프 없음",
                                   List.of("조회된 스태프가 없습니다."));
        }

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/staffs/{username}")
    public ResponseEntity<UserResponse>
    getStaffUser(@PathVariable String username) {
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
                .orElseThrow(() ->  new ApiException(HttpStatus.NO_CONTENT,
                                                   "스태프 없음",
                                                   List.of("조회된 스태프가 없습니다.")));
    }

    @GetMapping("/superusers")
    public ResponseEntity<List<UserResponse>>
    listSuperUsers() {
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
            throw new ApiException(HttpStatus.NO_CONTENT,
                                   "슈퍼유저 없음",
                                   List.of("조회된 슈퍼유저가 없습니다."));
        }

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/superusers/{username}")
    public ResponseEntity<UserResponse>
    getSuperUser(@PathVariable String username) {
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
                .orElseThrow(() -> new ApiException(HttpStatus.NO_CONTENT,
                                                    "슈퍼유저 없음",
                                                    List.of("조회된 슈퍼유저가 없습니다.")));
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>>
    listGroups() {
        List<Group> groups = groupService.listGroups();

        if (groups.isEmpty()) {
            throw new ApiException(HttpStatus.NO_CONTENT,
                                   "그룹 없음",
                                   List.of("조회된 그룹이 없습니다."));
        }

        return ResponseEntity.ok().body(groups);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>>
    listPermissions() {
        List<Permission> permissions = permissionService.listPermissions();

        if (permissions.isEmpty()) {
            throw new ApiException(HttpStatus.NO_CONTENT,
                                   "권한 없음",
                                   List.of("조회된 권한이 없습니다."));
        }

        return ResponseEntity.ok().body(permissions);
    }

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
