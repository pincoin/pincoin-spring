package kr.pincoin.be.home.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import kr.pincoin.be.auth.dto.UserCreateRequest;
import kr.pincoin.be.auth.dto.UserResponse;
import kr.pincoin.be.auth.service.UserService;
import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.home.dto.RefreshTokenRequest;
import kr.pincoin.be.home.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    createUser(@Valid @RequestBody UserCreateRequest request) throws DataIntegrityViolationException,
                                                                     ConstraintViolationException {
        // @Valid - JSR-303 자바 표준 스펙
        // 특정 ArgumentResolver를 통해 진행되어 "컨트롤러 메소드의 유효성 검증"만 가능
        // 유효성 검증에 실패할 경우 MethodArgumentNotValidException 예외 발생

        // @Validated -  스프링 프레임워크 지원 스펙
        // AOP를 기반으로 스프링 빈의 유효성 검증을 위해 사용, 클래에는 @Validated, 메소드에는 @Valid
        // 유효성 검증에 실패할 경우 ConstraintViolationException 예외 발생
        try {
            UserResponse response = userService.createUser(request);
            return ResponseEntity.ok().body(response);
        } catch (DataIntegrityViolationException | ConstraintViolationException ignored) {
            throw new ApiException(HttpStatus.CONFLICT, "아이디 중복 오류", List.of("이미 사용 중인 아이디입니다."));
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
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED,
                                                    "로그인 실패",
                                                    List.of("아이디 또는 비밀번호가 올바르지 않습니다.")));
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
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED,
                                                    "로그인 실패",
                                                    List.of("아이디 또는 비밀번호가 올바르지 않습니다.")));
    }
}
