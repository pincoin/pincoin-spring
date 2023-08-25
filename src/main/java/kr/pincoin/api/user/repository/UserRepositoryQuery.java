package kr.pincoin.api.user.repository;

import kr.pincoin.api.user.dto.UserResult;

import java.util.Optional;

public interface UserRepositoryQuery {
    Optional<UserResult> findUserByUsername(String username, Boolean active);
}