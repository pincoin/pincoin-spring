package kr.pincoin.be.home.controller;

import jakarta.validation.Valid;
import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@Slf4j
public class HomeController {
    private final AuthService authService;

    public HomeController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("")
    public String Home() {
        return "pincoin be";
    }

    @PostMapping("/access-token")
    public ResponseEntity<AccessTokenResponse>
    AccessToken(@Valid @RequestBody PasswordGrantRequest request) {
        AccessTokenResponse response = authService.authenticate(request);

        if (response != null) {
            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenResponse>
    RefreshToken() {
        return null;
    }
}
