package kr.pincoin.api.user.repository;

import kr.pincoin.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuery {
}