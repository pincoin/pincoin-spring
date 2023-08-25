package kr.pincoin.api.auth.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import kr.pincoin.api.auth.dto.AccessTokenResponse;
import kr.pincoin.api.auth.dto.PasswordGrantRequest;
import kr.pincoin.api.auth.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static kr.pincoin.api.auth.jwt.TokenProvider.ACCESS_TOKEN_EXPIRES_IN;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {
    private final TokenProvider tokenProvider;

    public AuthController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/sign-up")
    public String
    createUser() throws DataIntegrityViolationException,
                        ConstraintViolationException {
        // @Valid - JSR-303 자바 표준 스펙
        // 특정 ArgumentResolver를 통해 진행되어 "컨트롤러 메소드의 유효성 검증"만 가능
        // 유효성 검증에 실패할 경우 MethodArgumentNotValidException 예외 발생

        // @Validated -  스프링 프레임워크 지원 스펙
        // AOP를 기반으로 스프링 빈의 유효성 검증을 위해 사용, 클래에는 @Validated, 메소드에는 @Valid
        // 유효성 검증에 실패할 경우 ConstraintViolationException 예외 발생

        return "sign up";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AccessTokenResponse>
    authenticate(@Valid @RequestBody PasswordGrantRequest request) {
        log.debug("{} {}", request.getEmail(), request.getPassword());

        // jwt 반환
        // 중복 로그인 허용 여부
        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", "1L");
        claims.put("username", "john");
        claims.put("email", "test@example.com");

        String accessToken = tokenProvider.createAccessToken(claims);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Authorization", "Bearer " + accessToken);
        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(new AccessTokenResponse(accessToken, ACCESS_TOKEN_EXPIRES_IN));
    }

    @PostMapping("/refresh")
    public String
    refreshToken() {
        return "refresh";
    }
}
