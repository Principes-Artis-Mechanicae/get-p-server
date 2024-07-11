package es.princip.getp.domain.auth.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum LoginErrorCode implements ErrorCode {
    INCORRECT_EMAIL_OR_PASSWORD(HttpStatus.CONFLICT, "올바르지 않은 이메일 또는 비밀번호");

    private final HttpStatus status;
    private final ErrorDescription description;

    LoginErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.description = ErrorDescription.of(this.name(), message);
    }

    public HttpStatus status() {
        return status;
    }

    public ErrorDescription description() {
        return description;
    }
}