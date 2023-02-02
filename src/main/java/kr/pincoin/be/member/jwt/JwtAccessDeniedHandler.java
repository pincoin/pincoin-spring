package kr.pincoin.be.member.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        // 403 Forbidden
        // 서버가 요청을 이해했으나 권한이 없어 요청이 거부됨
        // 클라이언트가 요청에 대한 권한이 없어 요청을 정상적으로 처리할 수 없다.
        // 예: 로그인이 되었으나 다른 사용자의 마이페이지 접근 요청 시
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}