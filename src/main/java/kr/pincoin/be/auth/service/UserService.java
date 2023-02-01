package kr.pincoin.be.auth.service;

import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Mono<List<User>> listUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return Mono.empty();
        }
        return Mono.just(users);
    }
}
