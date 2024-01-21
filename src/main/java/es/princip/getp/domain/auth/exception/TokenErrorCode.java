package es.princip.getp.domain.auth.exception;

import es.princip.getp.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum TokenErrorCode implements ErrorCode {
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "만료되거나 유효하지 않은 Refresh Token");

    private final HttpStatus status;
    private final String message;

    TokenErrorCode(HttpStatus status, String message) {
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