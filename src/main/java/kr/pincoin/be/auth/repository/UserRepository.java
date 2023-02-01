package kr.pincoin.be.auth.repository;

import kr.pincoin.be.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();
}
