package kr.pincoin.api.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.api.user.dto.UserResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotNull
    @NotBlank
    private String username;

    @Column(name = "password")
    @NotNull
    @NotBlank
    private String password;

    @Column(name = "is_active")
    private Boolean active;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public User(@NotNull String username,
                @NotNull String password) {
        this.username = username;
        this.password = password;
        this.active = false;
    }

    public User(UserResult result) {
        this.id = result.getId();
        this.username = result.getUsername();
        this.password = result.getPassword();
        this.active = result.getActive();
    }

    public User activate() {
        active = true;
        return this;
    }

    public User inactivate() {
        active = false;
        return this;
    }

    public void grant(@NotNull Role role) {
        this.role = role;
    }

    public void revoke() {
        this.role = null;
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