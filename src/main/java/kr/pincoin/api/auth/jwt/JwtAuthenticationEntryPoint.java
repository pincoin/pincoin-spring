package kr.pincoin.api.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static kr.pincoin.api.auth.jwt.JwtFilter.*;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void
    commence(HttpServletRequest request,
             HttpServletResponse response,
             AuthenticationException authException) throws IOException {
        // 401 Unauthorized
        // 사용자가 로그인하지 않은 경우

        String exception = (String) (request.getAttribute("exception"));

        try {
            if (exception == null) {
                // 보안 설정이 아닌 다른 곳에서 데이터베이스 처리 등 예외가 발생
                log.warn(authException.getLocalizedMessage());
                setResponse(response,
                            ERROR_401_UNKNOWN,
                            "내부 시스템 오류");
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

    private void
    setResponse(HttpServletResponse response,
                String errorCode,
                String errorMessage) throws IOException, JSONException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.equals(ERROR_401_UNKNOWN)
                                   ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                                   : HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(new JSONObject()
                                           .put("message", errorMessage)
                                           .put("code", errorCode));
    }
}