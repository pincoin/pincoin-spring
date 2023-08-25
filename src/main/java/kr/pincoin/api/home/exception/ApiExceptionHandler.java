package kr.pincoin.api.home.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
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
import org.springframework.web.servlet.NoHandlerFoundException;
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
    // 503 handleAsyncRequestTimeoutException
    // 500 handleConversionNotSupported
    // 400 handleTypeMismatch
    // 400 handleHttpMessageNotReadable
    // 500 handleHttpMessageNotWritable

    @Override
    protected ResponseEntity<Object>
    handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex,
                                  @NonNull HttpHeaders headers,
                                  @NonNull HttpStatusCode status,
                                  @NonNull WebRequest request) {
        // application.properties에서 아래 속성이 있어야 예외 처리 가능
        // spring.web.resources.add-mappings=false
        // spring.mvc.throw-exception-if-no-handler-found=true
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND,
                                                         "요청 리소스 없음",
                                                         new ArrayList<>());

        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                 @NonNull HttpHeaders headers,
                                 @NonNull HttpStatusCode status,
                                 @NonNull WebRequest request) {
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

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object>
    handleAccessDeniedException(AccessDeniedException ignored) {
        // Postman 등에서 Bearer 헤더 없이 보낼 경우 FORBIDDEN 되지만 헤더 자체가 없기 때문에 에러 메시지는 확인할 수 없음
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiErrorResponse(HttpStatus.FORBIDDEN,
                                           "리소스 접근 불가",
                                           new ArrayList<>()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ApiErrorResponse>
    handleConstraintException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();

        ex.getConstraintViolations()
                .forEach(violation -> errors.add(String.format("%s %s",
                                                               violation.getPropertyPath().toString(),
                                                               violation.getMessage())));

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT,
                                           "제약조건 오류",
                                           errors));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<ApiErrorResponse>
    handleDataException(DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT,
                                           "무결성 오류",
                                           List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiErrorResponse>
    handleApiException(ApiException ex) {
        // 204 No Content 응답은 응답 메시지 본문을 보내도 응답이 씹힌다.
        return ResponseEntity
                .status(ex.getResponse().getStatus())
                .body(new ApiErrorResponse(ex.getResponse().getStatus(),
                                           ex.getResponse().getMessage(),
                                           ex.getResponse().getErrors()));
    }
}
