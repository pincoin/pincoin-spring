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
    public static final String ERROR_401_INVALID_SECRET_KEY = "1001";
    public static final String ERROR_401_EXPIRED_JWT = "1002";
    public static final String ERROR_401_INVALID_TOKEN = "1003";
    public static final String ERROR_401_USER_NOT_FOUND = "1004";
    public static final String ERROR_401_UNKNOWN = "1005";

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
                                    FilterChain chain) throws ServletException, IOException {
        // 1. 헤더에서 액세스 토큰 가져오기
        // 2. 액세스 토큰 파싱 유효성 검증
        Optional.ofNullable(tokenProvider.getBearerToken(request))
                .flatMap(token -> tokenProvider.validateAccessToken(token, request))
                .ifPresent(username -> {
                    UserDetails userDetails;
                    try {
                        // 3. 사용자 디비 조회
                        userDetails = userDetailsService.loadUserByUsername(username);

                        // 4. 컨텍스트에 사용자 인증 처리
                        // AuthenticationManager 또는 AuthenticationProvider 구현체에서 isAuthenticated() = true
                        // 인증 토큰을 통과한 경우에만 이 생성자로 authentication 객체 생성
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, // principal: userDetails
                                        null, // credentials
                                        List.of() // userDetails.getAuthorities()
                                );

                        // 인증부가기능(WebAuthenticationDetails, WebAuthenticationDetailsSource) 저장
                        // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // 현재 사용자가 인증되도록 설정 후 컨텍스트에 저장
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } catch (UsernameNotFoundException ignored) {
                        request.setAttribute("exception", ERROR_401_USER_NOT_FOUND);
                        log.warn("{} is not found.", username);
                    }
                });

        // 5. 이후 필터 수행
        chain.doFilter(request, response);
    }
}