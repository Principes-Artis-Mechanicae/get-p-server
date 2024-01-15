package es.princip.getp.domain.serviceTerm.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum ServiceTermErrorCode implements ErrorCode {
    SERVICE_TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 서비스 약관");

    private final HttpStatus status;
    private final String message;

    ServiceTermErrorCode(HttpStatus status, String message) {
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
