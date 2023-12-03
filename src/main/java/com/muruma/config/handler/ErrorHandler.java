package com.muruma.config.handler;

import com.muruma.adapter.jpa.exception.UserNotFoundException;
import com.muruma.config.ErrorCode;
import com.muruma.config.exception.GenericException;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String DEVELOP_PROFILE = "dev";
    private final HttpServletRequest request;

    @Value("${spring.profiles.active:}")
    private String profile;

    public ErrorHandler(final HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR.getReason(), e);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handle(GenericException e) {
        var status = HttpStatus.BAD_REQUEST;
        if (e.getClass() == UserNotFoundException.class) status = HttpStatus.NOT_FOUND;
        log.error(status.getReasonPhrase(), e);
        return buildError(status, e.getMessage(), e);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handle(SignatureException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST, ErrorCode.UNAUTHORIZED_USER.getReason(), null);
    }

    @ExceptionHandler(ClaimJwtException.class)
    public ResponseEntity<ErrorResponse> handle(ClaimJwtException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST, ErrorCode.UNAUTHORIZED_USER.getReason(), null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentTypeMismatchException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_ID.getReason(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handle(EmptyResultDataAccessException e) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), e);
        return buildError(HttpStatus.NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getReason(), e);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handle(BindException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST, e);
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus httpStatus, BindException e) {
        String message = String.format(
                Objects.requireNonNull(Objects.requireNonNull(e.getFieldError()).getDefaultMessage()),
                e.getFieldError().getField()
        );
        ErrorResponse error = ErrorResponse.builder()
                .message(message)
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus httpStatus, String message, Throwable e) {
        final var detail = DEVELOP_PROFILE.equals(profile) ? Arrays.toString(e.getStackTrace()) : null;
        ErrorResponse error = ErrorResponse.builder()
                .message(message)
                .detail(detail)
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

}
