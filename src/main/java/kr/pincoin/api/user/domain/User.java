package kr.pincoin.api.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.api.user.dto.UserResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "auth_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "password")
    @NotNull
    @NotBlank
    private String password;

    @Column(name = "username")
    @NotNull
    @NotBlank
    private String username;

    @Column(name = "first_name")
    @NotNull
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @NotBlank
    private String lastName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "is_superuser")
    private Boolean superuser;

    @Column(name = "is_staff")
    private Boolean staff;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "date_joined")
    @NotNull
    private LocalDateTime dateJoined;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    public User(@NotNull String username,
                @NotNull String password,
                @NotNull String firstName,
                @NotNull String lastName,
                @NotNull String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.superuser = false;
        this.staff = false;
        this.active = false;

        this.dateJoined = LocalDateTime.now();
    }

    public User(UserResult result) {
        this.id = result.getId();
        this.username = result.getUsername();
        this.password = result.getPassword();
        this.firstName = result.getFirstName();
        this.lastName = result.getLastName();
        this.email = result.getEmail();

        this.superuser = result.getSuperuser();
        this.staff = result.getStaff();
        this.active = result.getActive();

        this.dateJoined = result.getDateJoined();
    }

    public User activate() {
        active = true;
        return this;
    }

    public User inactivate() {
        active = false;
        return this;
    }

    /**
     * UserDetails 메소드 구현체
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료 여부
        return active;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠김 여부
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부
        return active;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화 여부
        return active;
    }
}