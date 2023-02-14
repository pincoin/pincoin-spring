package kr.pincoin.be.auth.service;

import jakarta.validation.ConstraintViolationException;
import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.auth.dto.*;
import kr.pincoin.be.auth.jwt.TokenProvider;
import kr.pincoin.be.auth.repository.UserRepository;
import kr.pincoin.be.home.dto.AccessTokenResponse;
import kr.pincoin.be.home.dto.PasswordGrantRequest;
import kr.pincoin.be.home.dto.RefreshTokenRequest;
import kr.pincoin.be.member.domain.DbRefreshToken;
import kr.pincoin.be.member.repository.DbRefreshTokenRepository;
import kr.pincoin.be.member.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kr.pincoin.be.auth.jwt.TokenProvider.ACCESS_TOKEN_EXPIRES_IN;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    private final DbRefreshTokenRepository dbRefreshTokenRepository;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ProfileRepository profileRepository,
                       DbRefreshTokenRepository dbRefreshTokenRepository,
                       TokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.dbRefreshTokenRepository = dbRefreshTokenRepository;
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
        return userRepository.findActiveUserWithDbRefreshToken(request.getRefreshToken(), LocalDateTime.now())
                .map(this::getAccessTokenResponse);
    }

    @Transactional
    public List<User>
    listActiveUsers() {
        return userRepository.findActiveUsers();
    }

    @Transactional
    @PreAuthorize("#username == authentication.principal.username")
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

        // 2. 리프레시 토큰 디비 저장을 위한 빈 레코드 추가
        dbRefreshTokenRepository.save(new DbRefreshToken(user));

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
    public UserResponse
    updateUser(UserUpdateRequest request) {
        return null;
    }

    @Transactional
    public UserResponse
    changeUserPassword(UserChangePasswordRequest request) {
        return null;
    }

    @Transactional
    public UserResponse
    deleteUser() {
        return null;
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

        DbRefreshToken refreshTokenFound = dbRefreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Failed to issue refresh token"));

        dbRefreshTokenRepository.save(refreshTokenFound.issueRefreshToken(refreshToken));

        return new AccessTokenResponse(accessToken, ACCESS_TOKEN_EXPIRES_IN, refreshToken);
    }
}
