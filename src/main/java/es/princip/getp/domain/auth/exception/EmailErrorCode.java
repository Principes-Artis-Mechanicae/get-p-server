package es.princip.getp.domain.auth.exception;

import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum EmailErrorCode implements ErrorCode {
    EMAIL_SERVER_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 서버 문제로 인해 전송 실패");

    private final HttpStatus status;
    private final ErrorDescription description;

    EmailErrorCode(HttpStatus status, String message) {
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