package es.princip.getp.domain.serviceTerm.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum ServiceTermErrorCode implements ErrorCode {
    SERVICE_TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 서비스 약관");

    private final HttpStatus status;
    private final ErrorDescription description;

    ServiceTermErrorCode(HttpStatus status, String message) {
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
