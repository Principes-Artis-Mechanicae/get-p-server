package es.princip.getp.infra.security.exception.handler;

import es.princip.getp.domain.auth.exception.LoginErrorCode;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiErrorResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadCredentialsHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResult> badCredentialsException() {
        return ApiResponse.error(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
    }
}