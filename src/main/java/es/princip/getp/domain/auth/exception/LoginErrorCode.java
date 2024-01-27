package es.princip.getp.domain.auth.exception;

import es.princip.getp.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum LoginErrorCode implements ErrorCode {
    INCORRECT_EMAIL_OR_PASSWORD(HttpStatus.CONFLICT, "올바르지 않은 이메일 또는 비밀번호");

    private final HttpStatus status;
    private final String message;

    LoginErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public String code() {
        return this.name();
    }

    public HttpStatus status() {
        return this.status;
    }
}