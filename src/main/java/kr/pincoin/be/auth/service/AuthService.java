package kr.pincoin.be.auth.service;

import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.auth.repository.UserRepository;
import kr.pincoin.be.member.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    public static final int TOKEN_EXPIRES_IN = 3600;

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    public AuthService(UserRepository userRepository,
                       TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public AccessTokenResponse authenticate(PasswordGrantRequest request) {
        return userRepository.findActiveUser(request.getUsername())
                .map(user -> new AccessTokenResponse(tokenProvider.createToken(user.getUsername(), user.getId()),
                                                 TOKEN_EXPIRES_IN))
                .orElse(null);
    }
}
