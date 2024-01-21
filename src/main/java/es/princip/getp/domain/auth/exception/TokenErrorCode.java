package es.princip.getp.domain.auth.exception;

import es.princip.getp.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum TokenErrorCode implements ErrorCode {
    EXPIRED_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "만료된 REFRESH TOKEN");

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