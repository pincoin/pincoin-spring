package kr.pincoin.be.member.service;


import kr.pincoin.be.auth.repository.UserRepository;
import kr.pincoin.be.member.domain.MemberDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    // Impl 구현체: 별도로 교체 가능한 빈 생성하지 않음
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MemberDetails(userRepository
                                         .findActiveUser(username)
                                         .orElseThrow(() -> new UsernameNotFoundException("가입 승인 완료 후 로그인 가능합니다.")));
    }
}
