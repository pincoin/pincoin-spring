package kr.pincoin.be.auth.service;

import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccessTokenResponse authenticate(PasswordGrantRequest request) {
        return userRepository.findActiveUser(request.getUsername())
                .map(user -> {
                    String accessToken = "토큰 가져가";
                    int expiresIn = 3600;
                    return new AccessTokenResponse(accessToken, expiresIn);
                }).orElse(null);
    }
}
