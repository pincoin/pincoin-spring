package kr.pincoin.be.home.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import kr.pincoin.be.auth.dto.UserCreateRequest;
import kr.pincoin.be.auth.dto.UserResponse;
import kr.pincoin.be.auth.service.UserService;
import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.home.dto.RefreshTokenRequest;
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
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "pincoin api";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse>
    createUser(@Valid @RequestBody UserCreateRequest request) {
        try {
            UserResponse response = userService.createUser(request);
            return ResponseEntity.ok().body(response);
        } catch (DataIntegrityViolationException | ConstraintViolationException ignored) {
            log.warn("username is duplicated: [{}]", request.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AccessTokenResponse>
    authenticate(@Valid @RequestBody PasswordGrantRequest request) {
        return userService.authenticate(request)
                .map(response -> {
                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.add("Authorization", "Bearer " + response.getAccessToken());
                    return ResponseEntity.ok().headers(responseHeaders).body(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse>
    refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return userService.refresh(request)
                .map(response -> {
                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.add("Authorization", "Bearer " + response.getAccessToken());
                    return ResponseEntity.ok().headers(responseHeaders).body(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
