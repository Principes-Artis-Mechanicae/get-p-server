package es.princip.getp.domain.auth.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiErrorResult;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResult badCredentialsException(final BadCredentialsException e) {
        return ApiResponse.error(HttpStatus.CONFLICT, "INCORRECT_EMAIL_OR_PASSWORD", "올바르지 않은 이메일 또는 비밀번호");
    }
}