package kr.pincoin.be.home.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;
    private final LocalDateTime timestamp;

    public ApiErrorResponse(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
        this.timestamp = LocalDateTime.now();
    }
}