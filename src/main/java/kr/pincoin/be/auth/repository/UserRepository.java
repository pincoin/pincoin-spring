package kr.pincoin.be.auth.repository;

import kr.pincoin.be.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.isActive = true")
    List<User> findActiveUsers();

    @Query(value = "SELECT u FROM User u WHERE u.isActive = false")
    List<User> findInactiveUsers();

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.isActive = true")
    Optional<User> findActiveUser(String username);

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.isActive = false")
    Optional<User> findInactiveUser(String username);

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.isActive = true" +
            " AND u.isStaff = true")
    List<User> findStaffUsers();

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.isActive = true" +
            " AND u.isStaff = true")
    Optional<User> findStaffUser(String username);

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.isActive = true" +
            " AND u.isSuperuser = true")
    List<User> findSuperUsers();

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.isActive = true" +
            " AND u.isSuperuser = true")
    Optional<User> findSuperUser(String username);
}
