package kr.pincoin.be.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private boolean isSuperuser;

    private boolean isStaff;

    private boolean isActive;

    private LocalDateTime lastLogin;

    private LocalDateTime dateJoined;

    public UserResponse(String username,
                        String firstName,
                        String lastName,
                        String email,
                        boolean isSuperuser,
                        boolean isStaff,
                        boolean isActive,
                        LocalDateTime lastLogin,
                        LocalDateTime dateJoined) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isSuperuser = isSuperuser;
        this.isStaff = isStaff;
        this.isActive = isActive;
        this.lastLogin = lastLogin;
        this.dateJoined = dateJoined;
    }
}
