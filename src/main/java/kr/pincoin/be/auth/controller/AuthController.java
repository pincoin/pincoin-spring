package kr.pincoin.be.auth.controller;

import jakarta.validation.Valid;
import kr.pincoin.be.auth.dto.AccessTokenResponse;
import kr.pincoin.be.auth.dto.PasswordGrantRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {
    @GetMapping("")
    public String Home() {
        return "Auth controller";
    }

    @PostMapping("/access-token")
    public ResponseEntity<AccessTokenResponse>
    AccessToken(@Valid @RequestBody PasswordGrantRequest request) {
        log.debug(request.getGrantType());
        log.debug(request.getUsername());
        log.debug(request.getPassword());

        AccessTokenResponse response = new AccessTokenResponse("access_token",
                                                               "token_type",
                                                               "expires_in",
                                                               "refresh_token");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenResponse>
    RefreshToken() {
        return null;
    }
}
