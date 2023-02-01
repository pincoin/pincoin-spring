package kr.pincoin.be.auth.service;

import kr.pincoin.be.auth.domain.User;
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
