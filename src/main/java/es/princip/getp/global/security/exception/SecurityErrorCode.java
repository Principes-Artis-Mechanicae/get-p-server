package es.princip.getp.global.security.exception;

import es.princip.getp.global.exception.ErrorCode;
import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum SecurityErrorCode implements ErrorCode {
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한 없음");

    private final HttpStatus status;
    private final ErrorDescription description;

    SecurityErrorCode(HttpStatus status, String message) {
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
