package kr.pincoin.be.home.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    // 미구현 예외처리 목록
    // 405 handleHttpRequestMethodNotSupported
    // 415 handleHttpMediaTypeNotSupported
    // 406 handleHttpMediaTypeNotAcceptable
    // 500 handleMissingPathVariable
    // 400 handleMissingServletRequestParameter
    // 400 handleMissingServletRequestPart
    // 400 handleServletRequestBindingException
    // 404 handleNoHandlerFoundException
    // 503 handleAsyncRequestTimeoutException
    // 500 handleConversionNotSupported
    // 400 handleTypeMismatch
    // 400 handleHttpMessageNotReadable
    // 500 handleHttpMessageNotWritable

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        log.error(ex.getLocalizedMessage());

        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
                                                         "잘못된 입력 파라미터 형식",
                                                         errors);

        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiErrorResponse(HttpStatus.FORBIDDEN,
                                           "리소스 접근 불가",
                                           new ArrayList<>()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ApiErrorResponse> handleDataException() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT,
                                           "UNIQUE 필드 중복 오류",
                                           new ArrayList<>()));
    }

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getResponse().getStatus())
                .body(new ApiErrorResponse(ex.getResponse().getStatus(),
                                           ex.getResponse().getMessage(),
                                           ex.getResponse().getErrors()));
    }
}
