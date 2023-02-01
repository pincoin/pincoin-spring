package kr.pincoin.be.auth.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String password;

    private LocalDateTime lastLogin;

    @NotNull
    private boolean isSuperuser;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    private boolean isStaff;

    @NotNull
    private boolean isActive;

    @NotNull
    private LocalDateTime dateJoined;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateJoined = LocalDateTime.now();

        this.isActive = false;
        this.isStaff = false;
        this.isSuperuser = false;
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        this(username, password, email);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void login() {
        this.lastLogin = LocalDateTime.now();
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void appointStaff() {
        this.isStaff = true;
    }

    public void dismissStaff() {
        this.isStaff = false;
    }

    public void appointSuperuser() {
        this.isSuperuser = true;
    }

    public void dismissSuperuser() {
        this.isSuperuser = false;
    }
}
