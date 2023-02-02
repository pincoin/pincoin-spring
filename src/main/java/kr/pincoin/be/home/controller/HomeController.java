package kr.pincoin.be.home.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import kr.pincoin.be.auth.service.AuthService;
import kr.pincoin.be.auth.service.UserService;
import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.member.dto.UserCreateRequest;
import kr.pincoin.be.member.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@Slf4j
public class HomeController {
    private final AuthService authService;

    private final UserService userService;

    public HomeController(AuthService authService,
                          UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("")
    public String Home() {
        return "pincoin be";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse>
    UserCreate(@Valid @RequestBody UserCreateRequest request) {
        try {
            UserResponse response = userService.createUser(request);
            return ResponseEntity.ok().body(response);
        } catch (DataIntegrityViolationException | ConstraintViolationException ignored) {
            log.warn("username is duplicated: [{}]", request.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/access-token")
    public ResponseEntity<AccessTokenResponse>
    AccessToken(@Valid @RequestBody PasswordGrantRequest request) {
        AccessTokenResponse response = authService.authenticate(request);

        if (response != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Authorization", "Bearer " + response.getAccessToken());
            return ResponseEntity.ok().headers(responseHeaders).body(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenResponse>
    RefreshToken() {
        return null;
    }
}
