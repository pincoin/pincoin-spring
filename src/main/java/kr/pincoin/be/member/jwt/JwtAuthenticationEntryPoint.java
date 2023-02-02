package kr.pincoin.be.member.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 401 Unauthorized
        // 유효한 자격증명을 제공하지 않아 요청이 거부됨
        // 클라이언트가 인증되지 않았기 때문에 요청을 정상적으로 처리할 수 없다.
        // 예: 로그인이 되지 않은 상태에서 마이페이지 접근 요청 시
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}