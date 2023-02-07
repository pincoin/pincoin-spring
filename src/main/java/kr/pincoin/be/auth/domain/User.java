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

    @Column(name = "password")
    @NotNull
    @NotBlank
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_superuser")
    @NotNull
    private boolean superuser;

    @Column(name = "username")
    @NotNull
    @NotBlank
    private String username;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "email")
    @NotNull
    @NotBlank
    @Email
    private String email;

    @Column(name = "is_staff")
    @NotNull
    private boolean staff;

    @Column(name = "is_active")
    @NotNull
    private boolean active;

    @Column(name = "date_joined")
    @NotNull
    private LocalDateTime dateJoined;

    /*
     @OneToOne 양방향 매핑은 LAZY 로딩이 동작하지 않는다.
     참조하는 쪽에서 JOIN FETCH
     @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
     private Profile profile;
     @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
     private RefreshToken refreshToken;
    */

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateJoined = LocalDateTime.now();

        this.active = false;
        this.staff = false;
        this.superuser = false;
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        this(username, password, email);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User activate() {
        this.active = true;
        return this;
    }

    public void changeName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void login() {
        this.lastLogin = LocalDateTime.now();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }
}
