package kr.pincoin.be.member.domain;

import kr.pincoin.be.auth.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record MemberDetails(User user) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠김 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화 여부
        return true;
    }
}
