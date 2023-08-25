package kr.pincoin.api.home.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ApiException extends RuntimeException {
    private final ApiErrorResponse response;

    public ApiException(HttpStatus status, String message, List<String> errors) {
        this.response = new ApiErrorResponse(status, message, errors);
    }

    public ApiException(HttpStatus status, String message, String error) {
        this.response = new ApiErrorResponse(status, message, error);
    }
}
