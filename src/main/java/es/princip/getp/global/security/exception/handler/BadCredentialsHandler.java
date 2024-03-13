package es.princip.getp.global.security.exception.handler;

import es.princip.getp.domain.auth.exception.LoginErrorCode;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadCredentialsHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResult> badCredentialsException() {
        HttpStatus status = LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD.status();
        return ResponseEntity.status(status)
            .body(ApiResponse.error(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD));
    }
}