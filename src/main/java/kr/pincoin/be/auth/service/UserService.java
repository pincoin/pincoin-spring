package kr.pincoin.be.auth.service;

import jakarta.validation.ConstraintViolationException;
import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.auth.dto.UserCreateRequest;
import kr.pincoin.be.auth.dto.UserResponse;
import kr.pincoin.be.auth.jwt.TokenProvider;
import kr.pincoin.be.auth.repository.UserRepository;
import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.home.dto.RefreshTokenRequest;
import kr.pincoin.be.member.domain.RedisRefreshToken;
import kr.pincoin.be.member.repository.ProfileRepository;
import kr.pincoin.be.member.repository.RedisRefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.pincoin.be.auth.jwt.TokenProvider.ACCESS_TOKEN_EXPIRES_IN;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ProfileRepository profileRepository,
                       RedisRefreshTokenRepository redisRefreshTokenRepository,
                       TokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.redisRefreshTokenRepository = redisRefreshTokenRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<AccessTokenResponse>
    authenticate(PasswordGrantRequest request) {
        return userRepository.findActiveUser(request.getUsername())
                .map(user -> {
                    AccessTokenResponse response = null;
                    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        response = getAccessTokenResponse(user);
                    }
                    return Optional.ofNullable(response);
                })
                .orElseGet(Optional::empty);
    }

    @Transactional
    public Optional<AccessTokenResponse>
    refresh(RefreshTokenRequest request) {
        return redisRefreshTokenRepository.findByRefreshToken(request.getRefreshToken())
                .map(this::getAccessTokenResponse);
    }

    @Transactional
    public List<User>
    listActiveUsers() {
        return userRepository.findActiveUsers();
    }

    @Transactional
    public Optional<User>
    getActiveUser(String username) {
        return userRepository.findActiveUser(username);
    }

    @Transactional
    public UserResponse
    createUser(UserCreateRequest request) throws DataIntegrityViolationException,
                                                 ConstraintViolationException {
        // 프론트엔드 체크 항목
        // 중복회원검사
        // 본인인증: 휴대폰인증, 이메일인증
        // 약관동의 (필수는 디비 저장 안 하고 옵션 사항만 저장)

        // 1. 사용자 저장
        User user = userRepository.save(new User(request.getUsername(),
                                                 passwordEncoder.encode(request.getPassword()),
                                                 request.getEmail(),
                                                 request.getFirstName(),
                                                 request.getLastName())
                                                .activate());

        // 2. 리프레시 토큰 저장을 위한 빈 레코드 추가 (디비 저장 시)
        // refreshTokenRepository.save(new DbRefreshToken(user));

        return new UserResponse(user.getUsername(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.isSuperuser(),
                                user.isStaff(),
                                user.isActive(),
                                user.getLastLogin(),
                                user.getDateJoined());
    }

    @Transactional
    public List<User>
    listStaffUsers() {
        return userRepository.findStaffUsers();
    }

    @Transactional
    public Optional<User>
    getStaffUser(String username) {
        return userRepository.findStaffUser(username);
    }

    @Transactional
    public List<User>
    listSuperUsers() {
        return userRepository.findSuperUsers();
    }

    @Transactional
    public Optional<User>
    getSuperUser(String username) {
        return userRepository.findSuperUser(username);
    }

    private AccessTokenResponse getAccessTokenResponse(User user) {
        // 1. 기존 리프레시 토큰 모두 삭제
        redisRefreshTokenRepository.deleteAll(redisRefreshTokenRepository.findByUserId(user.getId()));

        // 2. 액세스 토큰 생성 (디비 저장 안 함)
        String accessToken = tokenProvider.createAccessToken(user.getUsername(), user.getId());

        // 3. 리프레시 토큰 생성 (디비 저장)
        String refreshToken = tokenProvider.createRefreshToken();
        // dbRefreshTokenRepository.save(new DbRefreshToken(user).issueRefreshToken(refreshToken));
        redisRefreshTokenRepository.save(new RedisRefreshToken(user.getId(), user.getUsername(), user.isActive())
                        .issueRefreshToken(refreshToken));

        return new AccessTokenResponse(accessToken, ACCESS_TOKEN_EXPIRES_IN, refreshToken);
    }

    private AccessTokenResponse getAccessTokenResponse(RedisRefreshToken token) {
        // 1. 기존 리프레시 토큰 모두 삭제
        redisRefreshTokenRepository.deleteAll(redisRefreshTokenRepository.findByUserId(token.getUserId()));

        // 2. 액세스 토큰 생성 (디비 저장 안 함)
        String accessToken = tokenProvider.createAccessToken(token.getUsername(), token.getId());

        // 3. 리프레시 토큰 생성 (Redis 저장)
        String refreshToken = tokenProvider.createRefreshToken();
        redisRefreshTokenRepository.save(new RedisRefreshToken(token.getUserId(), token.getUsername(), token.isActive())
                                                 .issueRefreshToken(refreshToken));

        return new AccessTokenResponse(accessToken, ACCESS_TOKEN_EXPIRES_IN, refreshToken);
    }
}
