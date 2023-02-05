package kr.pincoin.be.auth.service;

import jakarta.validation.ConstraintViolationException;
import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.auth.dto.UserCreateRequest;
import kr.pincoin.be.auth.dto.UserResponse;
import kr.pincoin.be.auth.repository.UserRepository;
import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.home.dto.RefreshTokenRequest;
import kr.pincoin.be.member.domain.RefreshToken;
import kr.pincoin.be.member.jwt.TokenProvider;
import kr.pincoin.be.member.repository.ProfileRepository;
import kr.pincoin.be.member.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kr.pincoin.be.member.jwt.TokenProvider.ACCESS_TOKEN_EXPIRES_IN;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ProfileRepository profileRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       TokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.refreshTokenRepository = refreshTokenRepository;
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
        return userRepository.findActiveUserWithRefreshToken(request.getRefreshToken(), LocalDateTime.now())
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

        // 2. 리프레시 토큰 레코드 추가(아직 토큰 생성 안 함- > 향후 Redis로 교체)
        refreshTokenRepository.save(new RefreshToken(user));

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
        // 1. 액세스 토큰 생성 (디비 저장 안 함)
        String accessToken = tokenProvider.createAccessToken(user.getUsername(), user.getId());

        // 2. 리프레시 토큰 생성 (디비 저장)
        String refreshToken = tokenProvider.createRefreshToken();

        RefreshToken refreshTokenFound = refreshTokenRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Failed to issue refresh token"));

        refreshTokenRepository.save(refreshTokenFound.issueRefreshToken(refreshToken));

        return new AccessTokenResponse(accessToken, ACCESS_TOKEN_EXPIRES_IN, refreshToken);
    }
}
