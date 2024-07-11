package es.princip.getp.domain.auth.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum TokenErrorCode implements ErrorCode {
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Access Token"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token");

    private final HttpStatus status;
    private final ErrorDescription description;

    TokenErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.description = ErrorDescription.of(this.name(), message);
    }

    public HttpStatus status() {
        return status;
    }

    public ErrorDescription description() { return description; }
}