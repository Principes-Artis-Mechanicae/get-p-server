package es.princip.getp.domain.client.exception;

import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum ClientErrorCode implements ErrorCode {
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 의뢰자 정보");

    private final HttpStatus status;
    private final ErrorDescription description;

    ClientErrorCode(HttpStatus status, String message) {
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