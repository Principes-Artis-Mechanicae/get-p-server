package es.princip.getp.domain.auth.exception.handler;

import es.princip.getp.domain.auth.exception.LoginErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiErrorResult> badCredentialsException(
        final BadCredentialsException exception) {
        HttpStatus status = LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD.status();
        return ResponseEntity.status(status)
            .body(ApiResponse.error(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD));
    }
}