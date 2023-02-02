package kr.pincoin.be.member.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;

    public JwtFilter(TokenProvider tokenProvider,
                     UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws
                                                       ServletException,
                                                       IOException {

        // 헤더에서 토큰 가져오기
        final Optional<String> token = Optional.ofNullable(tokenProvider.getBearerToken(request));

        if (token.isEmpty()) {
            // 토큰 포맷, 만료시각, 서명 유효성 검사 실패
            chain.doFilter(request, response);
            return;
        }

        final Optional<String> username = tokenProvider.getUsername(token.get());

        if (username.isEmpty()) {
            // 토큰 포맷, 만료시각, 서명 유효성 검사 실패
            chain.doFilter(request, response);
            return;
        }

        // 사용자정보 가져오기
        UserDetails userDetails;

        try {
            userDetails = userDetailsService.loadUserByUsername(username.get());
            log.debug("{} is authorized.", userDetails.getUsername());
        } catch (UsernameNotFoundException ignored) {
            chain.doFilter(request, response);
            return;
        }

        // AuthenticationManager 또는 AuthenticationProvider 구현체에서 isAuthenticated() = true
        // 인증 토큰을 통과한 경우에만 이 생성자로 authentication 객체를 만든다.
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,
                                                        // principal: userDetails
                                                        null, List.of()
                                                        // userDetails.getAuthorities()
                );

        // 인증부가기능(WebAuthenticationDetails, WebAuthenticationDetailsSource) 저장
        // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 컨텍스트에 인증을 설정 한 후 현재 사용자가 인증되도록 지정한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("{} is saved to security context.", userDetails.getUsername());

        // 스프링 시큐리티 필터들이 모두 정해진 순서대로 FilterChain에 엮인다.
        // 각자 로직을 수행하고 다음 필터 클래스의 doFilter() 메소드를 실행시킨다.
        chain.doFilter(request, response);
    }
}