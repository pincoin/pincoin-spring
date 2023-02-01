package kr.pincoin.be.auth.service;

import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.auth.dto.UserCreateRequest;
import kr.pincoin.be.auth.dto.UserResponse;
import kr.pincoin.be.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> listActiveUsers() {
        return userRepository.findActiveUsers();
    }

    @Transactional
    public Optional<User> getActiveUser(String username) {
        return userRepository.findActiveUser(username);
    }

    public UserResponse createUser(UserCreateRequest request) {
        // 비밀번호 암호화

        // 아이디 중복 오류 처리
        User user = userRepository.save(new User(request.getUsername(),
                                                 request.getPassword(),
                                                 request.getEmail(),
                                                 request.getFirstName(),
                                                 request.getLastName()));

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
