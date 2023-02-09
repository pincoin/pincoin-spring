package kr.pincoin.be.auth.repository;

import kr.pincoin.be.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.active = true")
    List<User> findActiveUsers();

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.active = false")
    List<User> findInactiveUsers();

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.active = true")
    Optional<User> findActiveUser(@Param("username") String username);

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.active = false")
    Optional<User> findInactiveUser(@Param("username") String username);

    @Query(value = "SELECT u" +
            " FROM DbRefreshToken rt" +
            " JOIN FETCH User u" +
            " ON u.id = rt.user.id " +
            " WHERE rt.refreshToken = :refreshToken" +
            " AND :now < rt.expiresIn" +
            " AND u.active = true")
    Optional<User> findActiveUserWithRefreshToken(@Param("refreshToken") String refreshToken,
                                                  @Param("now") LocalDateTime now);

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.active = true" +
            " AND u.staff = true")
    List<User> findStaffUsers();

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.active = true" +
            " AND u.staff = true")
    Optional<User> findStaffUser(@Param("username") String username);

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.active = true" +
            " AND u.superuser = true")
    List<User> findSuperUsers();

    @Query(value = "SELECT u" +
            " FROM User u" +
            " WHERE u.username = :username" +
            " AND u.active = true" +
            " AND u.superuser = true")
    Optional<User> findSuperUser(@Param("username") String username);
}
