package kr.pincoin.be.member.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static kr.pincoin.be.member.jwt.JwtFilter.*;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 401 Unauthorized
        // 유효한 자격증명을 제공하지 않아 요청이 거부됨
        // 클라이언트가 인증되지 않았기 때문에 요청을 정상적으로 처리할 수 없다.
        // 예: 로그인이 되지 않은 상태에서 마이페이지 접근 요청 시
        String exception = (String) (request.getAttribute("exception"));

        try {
            if (exception == null) {
                setResponse(response,
                            ERROR_401_UNKNOWN,
                            "알 수 없는 오류");
            } else if (exception.equals(ERROR_401_INVALID_SECRET_KEY)) {
                setResponse(response,
                            ERROR_401_INVALID_SECRET_KEY,
                            "잘못된 서명키");
            } else if (exception.equals(ERROR_401_EXPIRED_JWT)) {
                setResponse(response,
                            ERROR_401_EXPIRED_JWT,
                            "만료된 토큰");
            } else if (exception.equals(ERROR_401_INVALID_TOKEN)) {
                setResponse(response,
                            ERROR_401_INVALID_TOKEN,
                            "잘못된 토큰 형식");
            } else if (exception.equals(ERROR_401_USER_NOT_FOUND)) {
                setResponse(response,
                            ERROR_401_USER_NOT_FOUND,
                            "사용자 없음");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 커스텀 응답 처리를 위해 주석처리
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void setResponse(HttpServletResponse response,
                             String errorCode,
                             String errorMessage) throws IOException, JSONException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(new JSONObject()
                                           .put("message", errorMessage)
                                           .put("code", errorCode));
    }
}