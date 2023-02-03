package kr.pincoin.be.auth.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.be.member.domain.Profile;
import kr.pincoin.be.member.domain.RefreshToken;
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

    @Column(name = "is_superuser")
    @NotNull
    private boolean superuser;

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

    @Column(name = "is_staff")
    @NotNull
    private boolean staff;

    @Column(name = "is_active")
    @NotNull
    private boolean active;

    @NotNull
    private LocalDateTime dateJoined;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @OneToOne(mappedBy = "user")
    private RefreshToken token;

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
