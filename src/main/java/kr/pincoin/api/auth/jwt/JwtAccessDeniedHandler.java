package kr.pincoin.api.auth.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void
    handle(HttpServletRequest request,
           HttpServletResponse response,
           AccessDeniedException accessDeniedException) throws IOException {
        // 403 Forbidden
        // 로그인 완료 후 권한이 없는 페이지 (예, 다른 사람 마이페이지) 요청

        // Postman 등에서 Bearer 헤더 없이 보낼 경우 FORBIDDEN 되지만 헤더 자체가 없기 때문에 에러 메시지는 확인할 수 없음
        log.warn(accessDeniedException.getLocalizedMessage());
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}