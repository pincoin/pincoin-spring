package kr.pincoin.api.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserResult {
    private Integer id;

    private String password;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean superuser;

    private Boolean staff;

    private Boolean active;

    private LocalDateTime dateJoined;

    private LocalDateTime lastLogin;

    public UserResult(Integer id,
                      String password,
                      String username,
                      String firstName,
                      String lastName,
                      String email,
                      Boolean superuser,
                      Boolean staff,
                      Boolean active,
                      LocalDateTime dateJoined,
                      LocalDateTime lastLogin) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.superuser = superuser;
        this.staff = staff;
        this.active = active;
        this.dateJoined = dateJoined;
        this.lastLogin = lastLogin;
    }
}