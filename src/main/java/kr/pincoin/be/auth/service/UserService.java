package kr.pincoin.be.auth.service;

import jakarta.validation.ConstraintViolationException;
import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.member.dto.UserCreateRequest;
import kr.pincoin.be.member.dto.UserResponse;
import kr.pincoin.be.auth.repository.UserRepository;
import kr.pincoin.be.member.domain.Token;
import kr.pincoin.be.member.repository.ProfileRepository;
import kr.pincoin.be.member.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ProfileRepository profileRepository,
                       TokenRepository tokenRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<User> listActiveUsers() {
        return userRepository.findActiveUsers();
    }

    @Transactional
    public Optional<User> getActiveUser(String username) {
        return userRepository.findActiveUser(username);
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest request) throws DataIntegrityViolationException,
                                                                     ConstraintViolationException {
        User user = userRepository.save(new User(request.getUsername(),
                                                 passwordEncoder.encode(request.getPassword()),
                                                 request.getEmail(),
                                                 request.getFirstName(),
                                                 request.getLastName()));

        tokenRepository.save(new Token(user));

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
    public List<User> listStaffUsers() {
        return userRepository.findStaffUsers();
    }

    @Transactional
    public Optional<User> getStaffUser(String username) {
        return userRepository.findStaffUser(username);
    }

    @Transactional
    public List<User> listSuperUsers() {
        return userRepository.findSuperUsers();
    }

    @Transactional
    public Optional<User> getSuperUser(String username) {
        return userRepository.findSuperUser(username);
    }
}
